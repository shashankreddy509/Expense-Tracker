package com.techradicle.expensetracker.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.techradicle.expensetracker.domain.model.*
import kotlinx.coroutines.flow.Flow

typealias ReceiptPagingData = PagingData<ReceiptData>
typealias SignOutResponse = Response<Boolean>
typealias SettingsValuesResponse = Response<Boolean>
typealias SettingsDataResponse = Response<SettingsData>

interface DashboardRepository {
    val user: User

    suspend fun uploadImageToStorage(uri: Uri, filePath: String): Response<ImageUploadData>

    suspend fun addImageToDatabase(imageData: ImageUploadData): Response<Boolean>

    fun getReceiptsFromFirestore(): Flow<ReceiptPagingData>

    suspend fun signOut(): SignOutResponse

    suspend fun saveSettingValues(settingsValues: SettingValues): SettingsValuesResponse

    suspend fun getSettingValues(): Response<SettingsData>

}