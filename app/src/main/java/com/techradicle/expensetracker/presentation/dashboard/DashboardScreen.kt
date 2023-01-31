package com.techradicle.expensetracker.presentation.dashboard

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.techradicle.expensetracker.BuildConfig
import com.techradicle.expensetracker.R
import com.techradicle.expensetracker.components.AppTopBar
import com.techradicle.expensetracker.components.AuthTopBar
import com.techradicle.expensetracker.components.ProgressBar
import com.techradicle.expensetracker.components.layouts.HorizontalContent
import com.techradicle.expensetracker.core.AppConstants.DASHBOARD
import com.techradicle.expensetracker.core.AppConstants.NO_RECORDS
import com.techradicle.expensetracker.core.AppConstants.TAG
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.presentation.dashboard.components.SignOut
import java.io.File
import java.util.*

@ExperimentalMaterial3Api
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navigateToReceiptDetailsScreen: (receiptId: String) -> Unit,
    navigateToAuthScreen: () -> Unit,
) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )

    val receipts = viewModel.getReceipts().collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            AppTopBar(
                title = DASHBOARD,
                navigateToAuthScreen = {
                    viewModel.signOut()
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (hasImage && imageUri != null) {
                    val bitmap: Bitmap =
                        MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                    viewModel.uploadImage(imageUri!!, Utils.getRequestData(bitmap))
                    hasImage = false
                }
                val refresh = receipts.loadState.refresh
                when {
                    refresh is LoadState.Loading -> ProgressBar()
                    receipts.itemCount > 0 -> {
                        HorizontalContent(
                            receiptsPaging = viewModel.getReceipts().collectAsLazyPagingItems(),
                            navigateToReceiptDetailsScreen = navigateToReceiptDetailsScreen
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    hasImage = false
                    val uri = getTmpFileUri(context)
                    imageUri = uri
                    cameraLauncher.launch(imageUri)
                },
                containerColor = colorResource(id = R.color.primary_dark),
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50))
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    )

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
                Log.e(TAG, "Url:- ${imageData.imageUrl.toString()}")
                Log.e(TAG, "imageData:- ${imageData.imageData}")
                LaunchedEffect(isImageAddedToStorage) {
                    viewModel.addImageToDatabase(imageData)
                }
            }
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            print(addImageToStorageResponse.e)
        }
    }
}

fun getTmpFileUri(context: Context): Uri {
    val tmpFile =
        File.createTempFile("tmp_image_file_${Date().time}", ".png", context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        tmpFile
    )
}