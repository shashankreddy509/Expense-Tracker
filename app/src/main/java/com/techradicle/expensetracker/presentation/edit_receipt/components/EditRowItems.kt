package com.techradicle.expensetracker.presentation.edit_receipt.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRowItems(text: String, merchantName: MutableState<String>) {
    Row {
        Text(
            text = text,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            fontSize = 21.sp,
            color = Color.Black
        )
        TextField(value = merchantName.value, onValueChange = {
            merchantName.value = it
        })
    }
}