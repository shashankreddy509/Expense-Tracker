package com.techradicle.expensetracker.presentation.dashboard.components.newlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techradicle.expensetracker.domain.model.ChartData

@ExperimentalMaterial3Api
@Composable
fun ReceiptContentDayWise(
    chartData: ChartData,
    navigateToDetailsScreen: (receiptId: String) -> Unit,
    navigateToDetailsHeaderScreen: () -> Unit,
) {
    Card(
        modifier = Modifier.padding(start = 4.dp, end = 8.dp, top = 8.dp, bottom = 4.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(modifier = Modifier
            .padding(8.dp)
            .clickable {
                navigateToDetailsHeaderScreen()
            }) {
            Text(
                text = chartData.date,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            )
            Text(
                text = chartData.amount.toString(),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            )
        }
        Divider()
        for (item in chartData.receipts) {
            val color = if (item.income!!) Color.Blue else Color.Red
            Row(modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navigateToDetailsScreen(item.id!!)
                }) {
                Text(
                    text = item.category!!,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f)
                )
                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .background(Color.White)
                ) {
                    Text(
                        text = item.description!!,
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = item.paymentMode!!,
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Text(
                    text = "$ ${item.total!!}",
                    fontSize = 16.sp,
                    color = color,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f)
                )
            }
        }
    }
}