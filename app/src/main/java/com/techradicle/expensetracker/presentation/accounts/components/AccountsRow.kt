package com.techradicle.expensetracker.presentation.accounts.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AccountsRow(title: String, value: String) {
    Row {
        Text(
            text = title,
            modifier = Modifier
                .weight(0.5f)
        )
        Text(
            text = "$ $value",
            modifier = Modifier
                .weight(0.5f),
            textAlign = TextAlign.End
        )
    }
}