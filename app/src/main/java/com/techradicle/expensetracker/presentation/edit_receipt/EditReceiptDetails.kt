package com.techradicle.expensetracker.presentation.edit_receipt

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.components.ProgressBar
import com.techradicle.expensetracker.components.TopBarWithBack
import com.techradicle.expensetracker.core.AppConstants.EDIT_RECEIPTS_DETAILS
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.presentation.edit_receipt.components.EditReceiptDetailsContent
import com.techradicle.expensetracker.presentation.receipt_details.ReceiptDetailsViewModel

@ExperimentalMaterial3Api
@Composable
fun EditReceiptDetails(
    receiptId: String,
    navigateBack: () -> Unit,
    viewModel: ReceiptDetailsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBarWithBack(title = EDIT_RECEIPTS_DETAILS, navigateBack = navigateBack)
        },
        content = { padding ->
            EditReceiptDetailsContent(
                receiptID = receiptId,
                padding = padding
            )
        }
    )

    when (val deleteReceiptResponse = viewModel.updateReceiptResponse) {
        is Response.Failure -> LaunchedEffect(Unit) {
            print(deleteReceiptResponse.e)
        }
        is Response.Loading -> ProgressBar()
        is Response.Success -> deleteReceiptResponse.data?.let { updated ->
            LaunchedEffect(key1 = updated) {
                if (updated)
                    navigateBack()
            }
        }
    }
}