package com.techradicle.expensetracker.presentation.dashboard.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.components.ProgressBar
import com.techradicle.expensetracker.domain.model.Response.*
import com.techradicle.expensetracker.presentation.dashboard.DashboardViewModel

@Composable
fun SignOut(
    viewModel: DashboardViewModel = hiltViewModel(),
    navigateToAuthScreen: (signedOut: Boolean) -> Unit
) {
    when (val signOutResponse = viewModel.signOutResponse) {
        is Loading -> ProgressBar()
        is Success -> signOutResponse.data?.let { signedOut ->
            LaunchedEffect(signedOut) {
                navigateToAuthScreen(signedOut)
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(signOutResponse.e)
        }
    }
}