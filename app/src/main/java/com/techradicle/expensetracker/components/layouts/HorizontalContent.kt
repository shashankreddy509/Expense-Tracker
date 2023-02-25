package com.techradicle.expensetracker.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.techradicle.expensetracker.R
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.domain.model.ReceiptData
import com.techradicle.expensetracker.presentation.dashboard.components.ChartView
import com.techradicle.expensetracker.presentation.dashboard.components.ReceiptContent

@ExperimentalMaterial3Api
@Composable
fun HorizontalContent(
    receiptsPaging: LazyPagingItems<ReceiptData>,
    navigateToReceiptDetailsScreen: (receiptId: String) -> Unit,
    maxMonthSpent: MutableState<String>,
    maxReceiptSpent: MutableState<String>,
) {
    Column {

        if (receiptsPaging.itemSnapshotList.items.isNotEmpty()) {
            val totalMonthlySpend = Utils.getTotal(receiptsPaging.itemSnapshotList.items)
            val color = if (maxReceiptSpent.value.isNotEmpty()) {
                if (maxReceiptSpent.value.toDouble() < totalMonthlySpend.toDouble()) {
                    colorResource(id = R.color.pastel_red)
                } else Color.White
            } else Color.White
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(color)
            ) {
                Text(
                    text = "Total spend in current month",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(4.dp)
                )
                Text(
                    text = totalMonthlySpend,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(4.dp)
                )
            }
            ChartView(Utils.getChartData(receiptsPaging.itemSnapshotList.items))
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(receiptsPaging) { receipt ->
                receipt?.let {
                    ReceiptContent(
                        receipt = receipt,
                        onClick = navigateToReceiptDetailsScreen,
                        maxMonthSpent = maxMonthSpent,
                        maxReceiptSpent = maxReceiptSpent
                    )
                }
            }
        }
    }
}