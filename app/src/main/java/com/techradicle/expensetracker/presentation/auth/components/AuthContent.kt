package com.techradicle.expensetracker.presentation.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.techradicle.expensetracker.presentation.auth.components.SignInButton

@Composable
fun AuthContent(
    padding: PaddingValues,
    onTapSignIn: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.BottomCenter
    ) {
        SignInButton(onClick = onTapSignIn)
    }
}