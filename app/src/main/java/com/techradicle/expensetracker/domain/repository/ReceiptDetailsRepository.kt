package com.techradicle.expensetracker.domain.repository

import com.techradicle.expensetracker.domain.model.ReceiptData
import com.techradicle.expensetracker.domain.model.Response
import kotlinx.coroutines.flow.Flow


typealias ReceiptResponse = Response<ReceiptData>

interface ReceiptDetailsRepository {

    fun getReceipt(receiptId: String): Flow<ReceiptResponse>
}