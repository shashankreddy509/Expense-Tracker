package com.techradicle.expensetracker.domain.model.ocr

data class ImageResult(
    val merchant_name: String,
    val date: String,
    val time: String,
    val total: Double,
    val items: List<Items>
)