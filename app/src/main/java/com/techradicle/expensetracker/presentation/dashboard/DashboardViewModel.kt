package com.techradicle.expensetracker.presentation.dashboard

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.techradicle.expensetracker.core.Utils.Companion.items
import com.techradicle.expensetracker.domain.model.ImageUploadData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.model.SettingValues
import com.techradicle.expensetracker.domain.repository.DashboardRepository
import com.techradicle.expensetracker.domain.repository.SettingsDataResponse
import com.techradicle.expensetracker.domain.repository.SettingsValuesResponse
import com.techradicle.expensetracker.domain.repository.SignOutResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: DashboardRepository,
) : ViewModel() {
    val user = repo.user

    var selectedItem by mutableStateOf(items[0])

    var imageUrl by mutableStateOf<Response<ImageUploadData>>(Response.Success(null))
        private set

    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
        private set

    var settingValuesResponse by mutableStateOf<SettingsValuesResponse>(Response.Success(false))
    var settingDataResponse by mutableStateOf<SettingsDataResponse>(Response.Success(null))

    fun uploadImage(uri: Uri, filePath: String) = viewModelScope.launch {
        imageUrl = Response.Loading
        imageUrl = repo.uploadImageToStorage(uri = uri, filePath = filePath)
    }

    fun addImageToDatabase(imageData: ImageUploadData) = viewModelScope.launch {
        repo.addImageToDatabase(imageData)
    }

    fun getReceipts() = repo.getReceiptsFromFirestore().cachedIn(viewModelScope)

    fun signOut() = viewModelScope.launch {
        signOutResponse = Response.Loading
        signOutResponse = repo.signOut()
    }

    fun saveSettingsValues(settingValues: SettingValues) = viewModelScope.launch {
        settingValuesResponse = Response.Loading
        settingValuesResponse = repo.saveSettingValues(settingValues)
    }

    fun getSettingValues() = viewModelScope.launch {
        settingDataResponse = Response.Loading
        settingDataResponse = repo.getSettingValues()
    }
}
