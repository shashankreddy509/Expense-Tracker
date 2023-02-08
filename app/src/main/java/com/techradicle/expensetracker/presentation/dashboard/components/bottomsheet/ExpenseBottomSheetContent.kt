package com.techradicle.expensetracker.presentation.dashboard.components.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable

@Composable
fun ExpenseBottomSheetContent(
    onCameraClicked: () -> Unit,
    onStorageClicked: () -> Unit,
    onManualClicked: () -> Unit
) {
    Column {
        ExpenseBottomSheetListItem(
            icon = Icons.Default.Camera,
            title = "Camera",
            onItemClick = onCameraClicked
        )
        ExpenseBottomSheetListItem(
            icon = Icons.Default.Image,
            title = "Storage",
            onItemClick = onStorageClicked
        )
        ExpenseBottomSheetListItem(
            icon = Icons.Default.Person,
            title = "Manual",
            onItemClick = onManualClicked
        )
    }
}