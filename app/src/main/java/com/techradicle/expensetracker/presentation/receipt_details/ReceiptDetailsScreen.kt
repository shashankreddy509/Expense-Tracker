package com.techradicle.expensetracker.presentation.receipt_details

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.techradicle.expensetracker.components.TopAppBarWithActions
import com.techradicle.expensetracker.core.AppConstants.RECEIPTS_DETAILS
import com.techradicle.expensetracker.presentation.receipt_details.components.ReceiptDetailContent

@ExperimentalMaterial3Api
@Composable
fun ReceiptDetailsScreen(
    receiptId: String,
    navigateBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBarWithActions(
                title = RECEIPTS_DETAILS,
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            ReceiptDetailContent(
                receiptID = receiptId,
                padding = padding
            )
        }
    )
}