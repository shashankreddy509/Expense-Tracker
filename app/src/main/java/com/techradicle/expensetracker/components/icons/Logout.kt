package com.techradicle.expensetracker.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.techradicle.expensetracker.core.AppConstants.LOGOUT

@Composable
fun Logout(navigateToAuthScreen: () -> Unit) {
    IconButton(onClick = navigateToAuthScreen) {
        Icon(Icons.Filled.ExitToApp, contentDescription = LOGOUT)
    }
}