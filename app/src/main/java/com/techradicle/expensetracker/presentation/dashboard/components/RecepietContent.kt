package com.techradicle.expensetracker.presentation.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techradicle.expensetracker.components.icons.ThumbImage
import com.techradicle.expensetracker.core.AppConstants.NO_VALUE
import com.techradicle.expensetracker.domain.model.ReceiptData

@ExperimentalMaterial3Api
@Composable
fun ReceiptContent(
    receipt: ReceiptData,
    onClick: (receiptId: String) -> Unit,
) {
    val receiptId = receipt.id ?: NO_VALUE
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = { onClick(receiptId) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            receipt.apply {
                ThumbImage(
                    url = imageUrl!!,
                    width = 64.dp,
                    height = 64.dp,
                    padding = 8.dp
                )
                Column {
                    Text(
                        text = storeName.toString(),
                        fontSize = 24.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight()
                    )
                    Text(
                        text = createdAt.toString(),
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}