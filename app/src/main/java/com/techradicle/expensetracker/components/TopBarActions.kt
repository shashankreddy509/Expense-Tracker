package com.techradicle.expensetracker.components

import androidx.compose.runtime.Composable
import com.techradicle.expensetracker.components.icons.Logout

@Composable
fun TopBarActions(
    navigateToAuthScreen: () -> Unit
) {
    Logout(navigateToAuthScreen= navigateToAuthScreen)
}