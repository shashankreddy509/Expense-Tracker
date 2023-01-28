package com.techradicle.expensetracker.data

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.techradicle.expensetracker.core.AppConstants.SIGN_IN_REQUEST
import com.techradicle.expensetracker.core.AppConstants.SIGN_UP_REQUEST
import com.techradicle.expensetracker.core.FirebaseConstants.CREATED_AT
import com.techradicle.expensetracker.core.FirebaseConstants.DISPLAY_NAME
import com.techradicle.expensetracker.core.FirebaseConstants.EMAIL
import com.techradicle.expensetracker.core.FirebaseConstants.PHOTO_URL
import com.techradicle.expensetracker.core.FirebaseConstants.USERS
import com.techradicle.expensetracker.domain.model.Response.*
import com.techradicle.expensetracker.domain.repository.AuthRepository
import com.techradicle.expensetracker.domain.repository.OneTapSignInResponse
import com.techradicle.expensetracker.domain.repository.SignInWithGoogleResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
    private val db: FirebaseFirestore
) : AuthRepository {
    override val isAuthenticated = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Success(signUpResult)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse {
        return try {
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                createUserInFirestore()
            }
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    private suspend fun createUserInFirestore() {
        auth.currentUser?.apply {
            val user = toUser()
            db.collection(USERS).document(uid).set(user).await()
        }
    }
}

fun FirebaseUser.toUser() = mapOf(
    CREATED_AT to FieldValue.serverTimestamp(),
    DISPLAY_NAME to displayName,
    EMAIL to email,
    PHOTO_URL to photoUrl?.toString()
)