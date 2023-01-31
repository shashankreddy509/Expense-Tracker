package com.techradicle.expensetracker.presentation.dashboard

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.techradicle.expensetracker.domain.model.ImageUploadData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.repository.DashboardRepository
import com.techradicle.expensetracker.domain.repository.SignOutResponse
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

    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
        private set

    fun uploadImage(uri: Uri, requestJson: String) = viewModelScope.launch {
        imageUrl = Response.Loading
        imageUrl = repo.uploadImageToStorage(uri, requestJson)
    }

    fun addImageToDatabase(imageData: ImageUploadData) = viewModelScope.launch {
        repo.addImageToDatabase(imageData)
    }

    fun getReceipts() = repo.getReceiptsFromFirestore().cachedIn(viewModelScope)

    fun signOut() = viewModelScope.launch {
        signOutResponse = Response.Loading
        signOutResponse = repo.signOut()
    }
}
