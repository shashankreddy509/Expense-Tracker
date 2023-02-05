package com.techradicle.expensetracker.domain.model.ocrdata

data class Item(
    val amount: Double,
    val description: String,
    val qty: Double = 1.0
)