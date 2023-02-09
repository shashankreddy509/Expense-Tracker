package com.techradicle.expensetracker.di

import android.app.Application
import android.content.Context
import androidx.paging.PagingConfig
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.techradicle.expensetracker.R
import com.techradicle.expensetracker.core.AppConstants.BASE_URL
import com.techradicle.expensetracker.core.AppConstants.SIGN_IN_REQUEST
import com.techradicle.expensetracker.core.AppConstants.SIGN_UP_REQUEST
import com.techradicle.expensetracker.core.FirebaseConstants.PAGE_SIZE
import com.techradicle.expensetracker.data.AuthRepositoryImpl
import com.techradicle.expensetracker.data.DashboardRepositoryImpl
import com.techradicle.expensetracker.data.ManualEntryRepositoryImpl
import com.techradicle.expensetracker.data.remote.OcrApi
import com.techradicle.expensetracker.data.ReceiptDetailsRepositoryImpl
import com.techradicle.expensetracker.domain.repository.AuthRepository
import com.techradicle.expensetracker.domain.repository.DashboardRepository
import com.techradicle.expensetracker.domain.repository.ManualEntryRepository
import com.techradicle.expensetracker.domain.repository.ReceiptDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseDatabase() = Firebase.database

    @Provides
    fun providesFirestore() = Firebase.firestore

    @Provides
    fun providesStorage() = Firebase.storage

    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    @Named(SIGN_IN_REQUEST)
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        ).setAutoSelectEnabled(true)
        .build()


    @Provides
    @Named(SIGN_UP_REQUEST)
    fun providesSignUpRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    @Provides
    fun providesAuthRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named(SIGN_IN_REQUEST)
        signInRequest: BeginSignInRequest,
        @Named(SIGN_UP_REQUEST)
        signUpRequest: BeginSignInRequest,
        db: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signUpRequest = signUpRequest,
        db = db
    )

    @Provides
    fun providesDashboardRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        storage: FirebaseStorage,
        firestore: FirebaseFirestore,
        config: PagingConfig,
        api: OcrApi
    ): DashboardRepository = DashboardRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        storage = storage,
        firestore = firestore,
        config = config,
        api = api
    )

    @Provides
    fun providesReceiptDetailsRepository(
        firestore: FirebaseFirestore,
        storage: FirebaseStorage,
    ): ReceiptDetailsRepository = ReceiptDetailsRepositoryImpl(
        firebaseFirestore = firestore,
        storage = storage
    )

    @Singleton
    @Provides
    fun providesOcrApi(): OcrApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(OcrApi::class.java)
    }

    @Provides
    fun provideManualEntryRepository(
        firestore: FirebaseFirestore
    ): ManualEntryRepository = ManualEntryRepositoryImpl(
        firestore = firestore
    )

    @Provides
    fun providePagingConfig() = PagingConfig(
        pageSize = PAGE_SIZE.toInt()
    )
}