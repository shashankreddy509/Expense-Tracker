package com.techradicle.expensetracker.presentation.manual_entry

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.components.ProgressBar
import com.techradicle.expensetracker.components.TopBarWithBack
import com.techradicle.expensetracker.core.AppConstants.ADD_NEW_RECEIPTS
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.presentation.manual_entry.components.AddReceiptContent

@ExperimentalMaterial3Api
@Composable
fun ReceiptManualEntryScreen(
    navigateBack: () -> Unit,
    viewModel: ReceiptManualEntryViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBarWithBack(title = ADD_NEW_RECEIPTS, navigateBack = navigateBack)
        },
        content = { padding ->
            AddReceiptContent(padding)
        }
    )

    when (val insertStatus = viewModel.insertStatus) {
        is Response.Failure -> LaunchedEffect(key1 = Unit) { Utils.print(insertStatus.e) }
        is Response.Loading -> ProgressBar()
        is Response.Success -> insertStatus.data?.let { status ->
            if (status) {
                navigateBack()
            }
        }
    }
}