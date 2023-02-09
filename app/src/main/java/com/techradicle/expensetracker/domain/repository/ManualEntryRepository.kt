package com.techradicle.expensetracker.domain.repository

import com.techradicle.expensetracker.domain.model.InsertReceiptData
import com.techradicle.expensetracker.domain.model.Response

typealias ManualEntryResponse = Response<Boolean>

interface ManualEntryRepository {

    suspend fun addReceiptDataToDatabase(insertReceiptData: InsertReceiptData): ManualEntryResponse
}