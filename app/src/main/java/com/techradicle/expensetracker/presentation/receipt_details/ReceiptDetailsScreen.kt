package com.techradicle.expensetracker.presentation.receipt_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.techradicle.expensetracker.components.TopAppBarWithActions
import com.techradicle.expensetracker.core.AppConstants.RECEIPTS_DETAILS

@ExperimentalMaterial3Api
@Composable
fun ReceiptDetailsScreen(navigateBack: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBarWithActions(
                title = RECEIPTS_DETAILS,
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text(text = RECEIPTS_DETAILS)
            }
        }
    )
}