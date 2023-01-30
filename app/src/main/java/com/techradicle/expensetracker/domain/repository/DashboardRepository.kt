package com.techradicle.expensetracker.domain.repository

import android.net.Uri
import com.techradicle.expensetracker.domain.model.ImageUploadData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.model.User


interface DashboardRepository {
    val user: User

    suspend fun uploadImageToStorage(uri: Uri, requestJson: String): Response<ImageUploadData>

    suspend fun getTextFromImage(requestJson: String)

    suspend fun addImageToDatabase(imageData: ImageUploadData): Response<Boolean>
}