package com.techradicle.expensetracker.components.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.techradicle.expensetracker.core.AppConstants
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.domain.model.ReceiptData
import com.techradicle.expensetracker.presentation.dashboard.components.newlayout.ReceiptContentDayWise
import com.techradicle.expensetracker.presentation.dashboard.components.newlayout.Header

@ExperimentalMaterial3Api
@Composable
fun HorizontalContent(
    receiptsPaging: LazyPagingItems<ReceiptData>,
    navigateToReceiptDetailsScreen: (receiptId: String) -> Unit,
    maxMonthSpent: MutableState<String>,
    maxReceiptSpent: MutableState<String>,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (receiptsPaging.itemSnapshotList.items.isNotEmpty()) {
            val chartData = Utils.getChartData(receiptsPaging.itemSnapshotList.items)
            var totalExpenses = 0.0
            var totalIncome = 0.0
            var total = 0.0
            for (data in chartData) {
                data.receipts.filter { it.income!!.not() }.forEach {
                    totalExpenses += it.total!!
                }
                data.receipts.filter { it.income!! }.forEach {
                    totalIncome += it.total!!
                }
                total = totalIncome - totalExpenses
            }
            totalExpenses = String.format("%.2f", totalExpenses).toDouble()
            totalIncome = String.format("%.2f", totalIncome).toDouble()
            total = String.format("%.2f", total).toDouble()

            Column(modifier = Modifier.fillMaxSize()) {
                Header(
                    totalExpenses,
                    totalIncome,
                    total,
                    AppConstants.INCOME,
                    AppConstants.EXPENSES,
                    AppConstants.TOTAL
                )
                LazyColumn {
                    item {
                        for (data in chartData) {
                            ReceiptContentDayWise(
                                data,
                                navigateToReceiptDetailsScreen,
                                { }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight(),
//            contentPadding = PaddingValues(8.dp),
//        ) {
//            items(receiptsPaging) { receipt ->
//                receipt?.let {
//                    ReceiptContent(
//                        receipt = receipt,
//                        onClick = navigateToReceiptDetailsScreen,
//                        maxMonthSpent = maxMonthSpent,
//                        maxReceiptSpent = maxReceiptSpent
//                    )
//                }
//            }
//        }
    }
}