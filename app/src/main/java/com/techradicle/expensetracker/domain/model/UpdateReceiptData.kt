package com.techradicle.expensetracker.domain.model

data class UpdateReceiptData(
    val storeName: String,
    val amount: Double,
    val date: String,
    val cardNum: String,
)
