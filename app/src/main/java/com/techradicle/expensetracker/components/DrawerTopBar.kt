package com.techradicle.expensetracker.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.techradicle.expensetracker.components.icons.NavigationIcon

@ExperimentalMaterial3Api
@Composable
fun DrawerTopBar(
    openNavigationDrawer: () -> Unit,
    title: String
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        navigationIcon = {
            NavigationIcon(
                openNavigationDrawer = openNavigationDrawer
            )
        },
        colors = getTopBarColors()
    )
}