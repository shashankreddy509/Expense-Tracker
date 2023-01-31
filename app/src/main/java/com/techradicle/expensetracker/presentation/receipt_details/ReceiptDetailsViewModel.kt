package com.techradicle.expensetracker.presentation.receipt_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.repository.ReceiptDetailsRepository
import com.techradicle.expensetracker.domain.repository.ReceiptResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptDetailsViewModel @Inject constructor(
    private val repo: ReceiptDetailsRepository
) : ViewModel() {

    var receiptDetailsResponse by mutableStateOf<ReceiptResponse>(Response.Loading)
        private set

    fun getReceipt(receiptId: String) = viewModelScope.launch {
        repo.getReceipt(receiptId = receiptId).collect { response ->
            receiptDetailsResponse = response
        }
    }
}