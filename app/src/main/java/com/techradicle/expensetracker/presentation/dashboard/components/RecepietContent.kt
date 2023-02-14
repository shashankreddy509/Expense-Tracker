package com.techradicle.expensetracker.presentation.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techradicle.expensetracker.components.icons.ThumbImage
import com.techradicle.expensetracker.core.AppConstants.NO_IMAGE
import com.techradicle.expensetracker.core.AppConstants.NO_VALUE
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.domain.model.ReceiptData

@ExperimentalMaterial3Api
@Composable
fun ReceiptContent(
    receipt: ReceiptData,
    onClick: (receiptId: String) -> Unit,
    maxMonthSpent: MutableState<String>,
    maxReceiptSpent: MutableState<String>,
) {
    val receiptId = receipt.id ?: NO_VALUE
    val color = if (maxReceiptSpent.value.isNotEmpty()) {
        if (maxReceiptSpent.value.toDouble() < receipt.total!!) {
            Color.Red
        } else Color.White
    } else Color.White
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        onClick = { onClick(receiptId) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            receipt.apply {
                imageUrl?.let {
                    ThumbImage(
                        url = it,
                        width = 64.dp,
                        height = 64.dp,
                        padding = 8.dp
                    )
                } ?: Icon(
                    imageVector = Icons.Default.HideImage,
                    contentDescription = NO_IMAGE,
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .padding(8.dp)
                )
                Column {
                    Text(
                        text = storeName.toString(),
                        fontSize = 24.sp,
                        color = Color.Black,
                        maxLines = 1,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight()
                    )
                    Row {
                        val dateCorrect = date?.let {
                            Utils.getCorrectDate(it, createdAt!!)
                        } ?: Utils.dateFormatter(createdAt!!)
                        Text(
                            text = "Date: $dateCorrect",
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .weight(0.5f)
                                .fillMaxHeight()
                        )
                        Text(
                            text = "Amount: $${total.toString()}",
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}