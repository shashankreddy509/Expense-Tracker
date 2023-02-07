package com.techradicle.expensetracker.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.techradicle.expensetracker.core.AppConstants

@Composable
fun DeleteIcon(
    onDeleteIconClick: () -> Unit
) {
    IconButton(onClick = onDeleteIconClick) {
        Icon(Icons.Filled.Delete, contentDescription = AppConstants.DELETE)
    }
}