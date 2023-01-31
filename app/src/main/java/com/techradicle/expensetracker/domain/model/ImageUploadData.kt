package com.techradicle.expensetracker.domain.model

import android.net.Uri

data class ImageUploadData(
    val imageUrl: Uri,
    val imageData: String,
    val words: List<String>,
    val cardNo: String,
    var isAdded: Boolean = false
)
