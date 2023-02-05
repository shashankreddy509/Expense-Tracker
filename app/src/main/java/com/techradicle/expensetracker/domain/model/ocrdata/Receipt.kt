package com.techradicle.expensetracker.domain.model.ocrdata

data class Receipt(
    val country: String,
    val credit_card_number: String,
    val credit_card_type: String,
    val currency: String,
    val date: String,
    val items: List<Item>,
    val merchant_address: Any,
    val merchant_name: String,
    val ocr_confidence: Double,
    val ocr_text: String,
    val payment_method: String,
    val receipt_no: String,
    val subtotal: Double,
    val tax: Double,
    val time: String,
    val tip: Double,
    val total: Double
)