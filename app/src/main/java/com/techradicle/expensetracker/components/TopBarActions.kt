package com.techradicle.expensetracker.components

import androidx.compose.runtime.Composable
import com.techradicle.expensetracker.components.icons.DeleteIcon
import com.techradicle.expensetracker.components.icons.EditIcon

@Composable
fun TopBarActions(
    onEditIconClick: (receiptId: String) -> Unit,
    receiptId: String,
    onDeleteIconClick: () -> Unit
) {
    EditIcon(onEditIconClick, receiptId)
    DeleteIcon(onDeleteIconClick)

}