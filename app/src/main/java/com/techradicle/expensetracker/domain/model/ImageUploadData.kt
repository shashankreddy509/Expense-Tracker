package com.techradicle.expensetracker.domain.model

import android.net.Uri
import com.techradicle.expensetracker.domain.model.ocr.ImageDataList

data class ImageUploadData(
    val imageUrl: Uri,
    var isAdded: Boolean = false,
    var imageData: ImageDataList
)
