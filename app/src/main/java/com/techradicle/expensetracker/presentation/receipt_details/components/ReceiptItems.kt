package com.techradicle.expensetracker.presentation.receipt_details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techradicle.expensetracker.domain.model.Items

@Composable
fun ReceiptItems(item: Items) {
    Text(
        text = item.description!!,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        fontSize = 16.sp,
        color = Color.DarkGray
    )
}