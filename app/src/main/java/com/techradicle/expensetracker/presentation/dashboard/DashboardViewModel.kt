package com.techradicle.expensetracker.presentation.dashboard

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techradicle.expensetracker.domain.model.ImageUploadData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: DashboardRepository
) : ViewModel() {
    val user = repo.user

    var imageUrl by mutableStateOf<Response<ImageUploadData>>(Response.Success(null))
        private set

    fun uploadImage(uri: Uri, requestJson: String) = viewModelScope.launch {
        imageUrl = Response.Loading
        imageUrl = repo.uploadImageToStorage(uri, requestJson)
    }

    fun getTextFromImage(requestJson: String) = viewModelScope.launch {
        repo.getTextFromImage(requestJson)
    }

    fun addImageToDatabase(imageData: ImageUploadData) = viewModelScope.launch {
        repo.addImageToDatabase(imageData)
    }


}
