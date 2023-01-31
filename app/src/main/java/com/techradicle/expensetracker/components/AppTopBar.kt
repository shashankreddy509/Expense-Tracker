package com.techradicle.expensetracker.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow

@ExperimentalMaterial3Api
@Composable
fun AppTopBar(
    title: String,
    navigateToAuthScreen: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            TopBarActions(navigateToAuthScreen = navigateToAuthScreen)
        },
        colors = getTopBarColors()
    )
}