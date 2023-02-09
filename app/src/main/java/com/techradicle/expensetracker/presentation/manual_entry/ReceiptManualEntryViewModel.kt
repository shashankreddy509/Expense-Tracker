package com.techradicle.expensetracker.presentation.manual_entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techradicle.expensetracker.domain.model.InsertReceiptData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.repository.ManualEntryRepository
import com.techradicle.expensetracker.domain.repository.ManualEntryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptManualEntryViewModel @Inject constructor(
    private val repo: ManualEntryRepository
) : ViewModel() {

    var insertStatus by mutableStateOf<ManualEntryResponse>(Response.Success(null))
        private set

    fun addReceiptDataToDatabase(insertReceiptData: InsertReceiptData) = viewModelScope.launch {
        insertStatus = Response.Loading
        insertStatus = repo.addReceiptDataToDatabase(insertReceiptData)
    }
}