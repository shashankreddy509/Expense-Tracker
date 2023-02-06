package com.techradicle.expensetracker.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    val icon:ImageVector,
    val name: String,
    val contentDescription:String
)