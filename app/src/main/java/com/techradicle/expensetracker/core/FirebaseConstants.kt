package com.techradicle.expensetracker.core

object FirebaseConstants {
    //Firebase Nodes and Collections
    const val USERS = "users"

    //Firebase Fields
    const val CREATED_AT = "createdAt"
    const val DISPLAY_NAME = "displayName"
    const val EMAIL = "email"
    const val PHOTO_URL = "photoUrl"

    //Firebase firestore colletions and documents
    const val RECEIPTS = "receipts"
    const val IMAGE_URL = "imageUrl"
    const val IMAGE_DATA = "imageData"
    const val UID = "uid"

    //Paging Limit
    const val PAGE_SIZE = 8L

    //Base URLs
    const val STORAGE_BASE_URL = "gs://expense-tracker-51866.appspot.com"
}