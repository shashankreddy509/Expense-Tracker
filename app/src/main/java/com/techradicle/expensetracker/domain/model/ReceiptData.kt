package com.techradicle.expensetracker.domain.model

import com.google.firebase.firestore.FieldValue

data class ReceiptData(
    val timestamp: FieldValue,
    val imageUrl: String,
    val data: String
)
