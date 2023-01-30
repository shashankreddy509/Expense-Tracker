package com.techradicle.expensetracker.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.techradicle.expensetracker.domain.model.ImageUploadData
import com.techradicle.expensetracker.domain.model.ReceiptData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.model.User
import kotlinx.coroutines.flow.Flow

typealias ReceiptPagingData = PagingData<ReceiptData>

interface DashboardRepository {
    val user: User

    suspend fun uploadImageToStorage(uri: Uri, requestJson: String): Response<ImageUploadData>

    suspend fun addImageToDatabase(imageData: ImageUploadData): Response<Boolean>

    fun getReceiptsFromFirestore(): Flow<ReceiptPagingData>

}