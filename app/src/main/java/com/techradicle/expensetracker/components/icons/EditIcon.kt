package com.techradicle.expensetracker.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.techradicle.expensetracker.core.AppConstants.EDIT

@Composable
fun EditIcon(
    onEditIconClick: (receiptId: String) -> Unit,
    receiptId: String
) {
    IconButton(onClick = { onEditIconClick(receiptId) }) {
        Icon(Icons.Filled.Edit, contentDescription = EDIT)
    }
}