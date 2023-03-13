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
    val date: String? = null,
    val items: List<Items>? = null,
    val cardNo: String? = null,
    val fileName: String? = null,
    val income: Boolean? = null,
    val category: String? = null,
    val description: String? = null,
    val paymentMode: String? = null
)


data class Items(
    val amount: Double? = null,
    val description: String? = null,
    val qty: Double? = null
)
