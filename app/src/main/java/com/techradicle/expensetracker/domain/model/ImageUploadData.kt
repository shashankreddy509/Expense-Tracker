package com.techradicle.expensetracker.domain.model

import android.net.Uri
import com.techradicle.expensetracker.domain.model.ocrdata.OcrImageData

data class ImageUploadData(
    val imageUrl: Uri,
    var isAdded: Boolean = false,
    var imageData: OcrImageData
)
