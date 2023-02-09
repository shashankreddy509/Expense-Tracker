package com.techradicle.expensetracker.domain.model

data class InsertReceiptData(
    val storeName: String,
    val total: Double,
    val date: String,
    val cardNo: String
)
