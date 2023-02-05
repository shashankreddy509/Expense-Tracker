package com.techradicle.expensetracker.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class ReceiptData(
    @ServerTimestamp
    val createdAt: Date? = null,
    val imageUrl: String? = null,
    val uid: String? = null,
    val id: String? = null,
    val storeName: String? = null,
    val total: Double? = null,
    val date: String? = null
)
