package com.techradicle.expensetracker.domain.model.ocrdata

data class OcrImageData(
    val ocr_type: String,
    val receipts: List<Receipt>,
    val success: Boolean
)