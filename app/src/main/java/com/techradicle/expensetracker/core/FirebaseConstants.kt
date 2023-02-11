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
    const val SETTINGS = "settings"

    //lables
    const val IMAGE_URL = "imageUrl"
    const val IMAGE_DATA = "imageData"
    const val UID = "uid"
    const val ID = "id"
    const val DATE = "date"
    const val STORE_NAME = "storeName"
    const val TOTAL = "total"
    const val ITEMS = "items"
    const val TIME = "time"
    const val CARD_NO = "cardNo"
    const val MODIFIED_ON = "modifiedOn"
    const val FILE_NAME = "fileName"
    const val MAX_AMOUNT_PER_RECEIPT = "maxAmountPerReceipt"
    const val MAX_AMOUNT_FOR_MONTH = "maxMonthAmount"

    //Paging Limit
    const val PAGE_SIZE = 8L

    //Base URLs
    const val STORAGE_BASE_URL = "gs://expense-tracker-51866.appspot.com"
}