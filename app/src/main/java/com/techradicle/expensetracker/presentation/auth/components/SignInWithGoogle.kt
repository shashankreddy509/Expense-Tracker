package com.techradicle.expensetracker.presentation.auth.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.components.ProgressBar
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.domain.model.Response.*
import com.techradicle.expensetracker.presentation.auth.AuthViewModel

@Composable
fun SignInWithGoogle(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHomeScreen: (signedIn: Boolean) -> Unit
) {
    when (val signInWithGoogleResponse = viewModel.signInWithGoogleResponse) {
        is Loading -> ProgressBar()
        is Failure -> LaunchedEffect(Unit) {
            Utils.print(signInWithGoogleResponse.e)
        }
        is Success -> {
            signInWithGoogleResponse.data?.let { signedIn ->
                LaunchedEffect(signedIn) {
                    navigateToHomeScreen(signedIn)
                }
            }
        }
    }
}