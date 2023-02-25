package com.techradicle.expensetracker.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.techradicle.expensetracker.core.FirebaseConstants
import com.techradicle.expensetracker.domain.model.InsertReceiptData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.repository.ManualEntryRepository
import com.techradicle.expensetracker.domain.repository.ManualEntryResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ManualEntryRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    firestore: FirebaseFirestore
) : ManualEntryRepository {

    var firestorCollection = firestore.collection(FirebaseConstants.RECEIPTS).document(auth.currentUser!!.uid).collection(
        FirebaseConstants.RECEIPTS
    )
    override suspend fun addReceiptDataToDatabase(insertReceiptData: InsertReceiptData): ManualEntryResponse {
        return try {
            val id = firestorCollection.document().id
            firestorCollection.document(id).set(
                mapOf(
                    FirebaseConstants.CREATED_AT to FieldValue.serverTimestamp(),
                    FirebaseConstants.ID to id,
                    FirebaseConstants.STORE_NAME to insertReceiptData.storeName,
                    FirebaseConstants.TOTAL to insertReceiptData.total,
                    FirebaseConstants.DATE to insertReceiptData.date,
                    FirebaseConstants.CARD_NO to insertReceiptData.cardNo,
                    FirebaseConstants.UID to auth.currentUser!!.uid,
                )
            ).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}