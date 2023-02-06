package com.techradicle.expensetracker.presentation.auth

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.techradicle.expensetracker.R
import com.techradicle.expensetracker.components.AuthTopBar
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.presentation.auth.components.OneTapSignIn
import com.techradicle.expensetracker.presentation.auth.components.SignInWithGoogle

@ExperimentalMaterial3Api
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            AuthTopBar(stringResource(id = R.string.app_name))
        },
        content = { padding ->
            AuthContent(
                padding = padding,
                onTapSignIn = {
                    viewModel.oneTapSignIn()
                }
            )
        }
    )

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credential =
                        viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                    val googleToken = credential.googleIdToken
                    val googleCredential = getCredential(googleToken, null)
                    viewModel.signInWithGoogle(googleCredential)
                } catch (e: ApiException) {
                    Utils.print(e)
                }
            }
        }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    OneTapSignIn {
        launch(it)
    }

    SignInWithGoogle { signedIn ->
        if (signedIn) {
            navigateToHomeScreen()
        }
    }
}