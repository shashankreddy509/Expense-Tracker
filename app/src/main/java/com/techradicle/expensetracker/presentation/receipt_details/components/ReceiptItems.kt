package com.techradicle.expensetracker.presentation.receipt_details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techradicle.expensetracker.domain.model.Items

@Composable
fun ReceiptItems(item: Items) {
    Row {
        Text(
            text = item.description!!,
            modifier = Modifier
                .weight(0.5f)
                .padding(top = 8.dp, bottom = 8.dp),
            fontSize = 16.sp,
            color = Color.DarkGray
        )
        Text(
            text = "${item.amount}",
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(0.5f)
                .padding(top = 8.dp, bottom = 8.dp, end = 16.dp),
            fontSize = 16.sp,
            color = Color.DarkGray
        )
    }
}