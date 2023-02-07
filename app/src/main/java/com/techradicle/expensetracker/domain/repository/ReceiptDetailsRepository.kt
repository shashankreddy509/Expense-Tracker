package com.techradicle.expensetracker.domain.repository

import com.techradicle.expensetracker.domain.model.ReceiptData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.model.UpdateReceiptData
import kotlinx.coroutines.flow.Flow


typealias ReceiptResponse = Response<ReceiptData>
typealias DeleteReceiptResponse = Response<Boolean>
typealias UpdateReceiptResponse = Response<Boolean>

interface ReceiptDetailsRepository {

    fun getReceipt(receiptId: String): Flow<ReceiptResponse>

    suspend fun updateReceipt(
        receiptId: String,
        updateReceiptData: UpdateReceiptData
    ): UpdateReceiptResponse

    suspend fun deleteReceipt(receiptId: String,fileName:String): DeleteReceiptResponse
}