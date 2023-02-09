package com.techradicle.expensetracker.presentation.edit_receipt.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRowItems(
    text: String,
    value: MutableState<String>,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    Row(modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 8.dp)) {
        Text(
            text = text,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .weight(0.3f),
            fontSize = 21.sp,
            color = Color.Black
        )
        TextField(
            modifier = Modifier.weight(0.7f),
            value = value.value,
            onValueChange = {
                value.value = it
            },
            keyboardOptions = keyboardOptions
        )
    }
}