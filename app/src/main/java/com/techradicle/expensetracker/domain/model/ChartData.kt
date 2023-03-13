package com.techradicle.expensetracker.domain.model

data class ChartData(val date: String, val amount: Double, val receipts: List<ReceiptData>)
