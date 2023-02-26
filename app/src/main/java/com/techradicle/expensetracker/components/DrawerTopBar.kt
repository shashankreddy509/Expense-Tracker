package com.techradicle.expensetracker.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@ExperimentalMaterial3Api
@Composable
fun DrawerTopBar(
    title: String
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        colors = getTopBarColors()
    )
}