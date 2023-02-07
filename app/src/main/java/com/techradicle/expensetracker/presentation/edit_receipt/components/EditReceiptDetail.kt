package com.techradicle.expensetracker.presentation.edit_receipt.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.components.ProgressBar
import com.techradicle.expensetracker.domain.model.ReceiptData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.presentation.receipt_details.ReceiptDetailsViewModel

@Composable
fun EditReceiptDetail(
    viewModel: ReceiptDetailsViewModel = hiltViewModel(),
    receiptDetailContent: @Composable (receiptData: ReceiptData) -> Unit
) {
    when (val receiptData = viewModel.receiptDetailsResponse) {
        is Response.Failure -> LaunchedEffect(key1 = Unit) {
            print(receiptData.e)
        }
        is Response.Loading -> ProgressBar()
        is Response.Success -> receiptData.data?.let { receipt ->
            receiptDetailContent(receipt)
        }
    }
}