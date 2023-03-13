package com.techradicle.expensetracker.presentation.dashboard

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.techradicle.expensetracker.BuildConfig
import com.techradicle.expensetracker.R
import com.techradicle.expensetracker.components.ProgressBar
import com.techradicle.expensetracker.components.layouts.HorizontalContent
import com.techradicle.expensetracker.core.AppConstants.NO_RECORDS
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.core.Utils.Companion.items
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.presentation.accounts.AccountsScreen
import com.techradicle.expensetracker.presentation.dashboard.components.SignOut
import com.techradicle.expensetracker.presentation.dashboard.components.bottombar.ExpenseTrackerBottomBar
import com.techradicle.expensetracker.presentation.dashboard.components.bottomsheet.ExpenseBottomSheetContent
import com.techradicle.expensetracker.presentation.dashboard.components.stats.StatsScreen
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navigateToReceiptDetailsScreen: (receiptId: String) -> Unit,
    navigateToAuthScreen: () -> Unit,
    navigateToReceiptManualScreen: () -> Unit
) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var fileUrl by remember { mutableStateOf("") }
    val selectedItem = remember { mutableStateOf(0) }
    val context = LocalContext.current

    val maxMonthSpent = remember { mutableStateOf("") }
    val maxReceiptSpent = remember { mutableStateOf("") }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.getSettingValues()
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    fun getTmpFileUri(context: Context): Uri {
        val tmpFile =
            File.createTempFile("${Date().time}", ".png", context.filesDir).apply {
                createNewFile()
//            deleteOnExit()
            }
        fileUrl = tmpFile.path
        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    val receipts = viewModel.getReceipts().collectAsLazyPagingItems()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            ExpenseBottomSheetContent(
                onCameraClicked = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                    val uri = getTmpFileUri(context)
                    imageUri = uri
                    cameraLauncher.launch(imageUri)
                },
                onStorageClicked = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                    imagePicker.launch("image/*")
                },
                onManualClicked = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                        navigateToReceiptManualScreen()
                    }
                }
            )
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = colorResource(id = R.color.primary_light),
        sheetPeekHeight = 0.dp
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentColor = Color.LightGray,
            content = { padding ->
                when (viewModel.selectedItem) {
                    items[0] -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            if (hasImage && imageUri != null) {
                                viewModel.uploadImage(imageUri!!, fileUrl)
                                hasImage = false
                                imageUri = null
                            }
                            val refresh = receipts.loadState.refresh
                            when {
                                refresh is LoadState.Loading -> ProgressBar()
                                receipts.itemCount > 0 -> {
                                    HorizontalContent(
                                        receiptsPaging = viewModel.getReceipts()
                                            .collectAsLazyPagingItems(),
                                        navigateToReceiptDetailsScreen = navigateToReceiptDetailsScreen,
                                        maxMonthSpent = maxMonthSpent,
                                        maxReceiptSpent = maxReceiptSpent
                                    )
                                }
                                receipts.itemCount == 0 -> {
                                    Text(
                                        text = NO_RECORDS,
                                        fontSize = 24.sp,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .fillMaxHeight()
                                    )
                                }
                                refresh is LoadState.Error -> print(refresh)
                            }
                        }
                    }
                    items[1] -> {
                        StatsScreen(padding = padding)
                    }
                    items[2] -> {
//                        DrawerItemSettings(
//                            padding = padding,
//                            maxMonthSpent = maxMonthSpent,
//                            maxReceiptSpent = maxReceiptSpent
//                        )
                        AccountsScreen(padding = padding)
                    }
                    items[2] -> {
                        viewModel.signOut()
                    }
                }
            },
            bottomBar = {
                ExpenseTrackerBottomBar(
                    selectedItem = selectedItem,
                    coroutineScope = coroutineScope,
                    bottomSheetScaffoldState = bottomSheetScaffoldState
                )
            }
        )

    }

    SignOut { signedOut ->
        if (signedOut) {
            navigateToAuthScreen()
        }
    }

    when (val addImageToStorageResponse = viewModel.imageUrl) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> {
            val isImageAddedToStorage = addImageToStorageResponse.data
            isImageAddedToStorage?.let { imageData ->
                LaunchedEffect(isImageAddedToStorage) {
                    if (!imageData.isAdded) {
                        viewModel.addImageToDatabase(imageData)
                        imageData.isAdded = true
                    }
                }
            }
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            print(addImageToStorageResponse.e)
        }
    }

    when (val saveSettings = viewModel.settingDataResponse) {
        is Response.Failure -> LaunchedEffect(key1 = Unit) { Utils.print(saveSettings.e) }
        is Response.Loading -> ProgressBar()
        is Response.Success -> saveSettings.data?.let { data ->
            maxReceiptSpent.value = (data.maxAmountPerReceipt ?: 0.0).toString()
            maxMonthSpent.value = (data.maxMonthAmount ?: 0.0).toString()
            viewModel.settingDataResponse = Response.Success(null)
        }
    }
}
