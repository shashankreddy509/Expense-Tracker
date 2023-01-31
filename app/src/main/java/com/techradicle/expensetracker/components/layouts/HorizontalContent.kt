package com.techradicle.expensetracker.components.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.techradicle.expensetracker.domain.model.ReceiptData
import com.techradicle.expensetracker.presentation.dashboard.components.ReceiptContent

@ExperimentalMaterial3Api
@Composable
fun HorizontalContent(
    receiptsPaging: LazyPagingItems<ReceiptData>,
    navigateToReceiptDetailsScreen: (receiptId: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(receiptsPaging) { receipt ->
            receipt?.let {
                ReceiptContent(receipt = receipt, onClick = navigateToReceiptDetailsScreen)
            }
        }
    }
}