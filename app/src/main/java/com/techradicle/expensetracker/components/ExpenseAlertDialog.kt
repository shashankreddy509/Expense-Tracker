package com.techradicle.expensetracker.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.techradicle.expensetracker.core.AppConstants.NO
import com.techradicle.expensetracker.core.AppConstants.YES

@Composable
fun ExpenseAlertDialog(
    openDialog: MutableState<Boolean>,
    title: String,
    text: String,
    onConfirm: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        onDismissRequest = {
            openDialog.value = false
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = YES)
            }
        },
        dismissButton = {
            Button(onClick = { openDialog.value = false }) {
                Text(text = NO)
            }
        }
    )
}