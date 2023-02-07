package com.techradicle.expensetracker.presentation.receipt_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.model.UpdateReceiptData
import com.techradicle.expensetracker.domain.repository.DeleteReceiptResponse
import com.techradicle.expensetracker.domain.repository.ReceiptDetailsRepository
import com.techradicle.expensetracker.domain.repository.ReceiptResponse
import com.techradicle.expensetracker.domain.repository.UpdateReceiptResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptDetailsViewModel @Inject constructor(
    private val repo: ReceiptDetailsRepository
) : ViewModel() {

    var receiptDetailsResponse by mutableStateOf<ReceiptResponse>(Response.Loading)
        private set

    var deleteReceiptResponse by mutableStateOf<DeleteReceiptResponse>(Response.Success(false))
        private set

    var updateReceiptResponse by mutableStateOf<UpdateReceiptResponse>(Response.Success(false))
        private set

    fun getReceipt(receiptId: String) = viewModelScope.launch {
        repo.getReceipt(receiptId = receiptId).collect { response ->
            receiptDetailsResponse = response
        }
    }

    fun updateReceipt(receiptId: String, updateReceiptData: UpdateReceiptData) =
        viewModelScope.launch {
            updateReceiptResponse = Response.Loading
            updateReceiptResponse =
                repo.updateReceipt(receiptId = receiptId, updateReceiptData = updateReceiptData)
        }

    fun deleteReceipt(receiptId: String,fileName:String) = viewModelScope.launch {
        deleteReceiptResponse = Response.Loading
        deleteReceiptResponse = repo.deleteReceipt(receiptId = receiptId,fileName=fileName)
    }
}