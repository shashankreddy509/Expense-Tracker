package com.techradicle.expensetracker.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.techradicle.expensetracker.core.FirebaseConstants
import com.techradicle.expensetracker.core.FirebaseConstants.RECEIPTS
import com.techradicle.expensetracker.domain.model.ReceiptData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.model.UpdateReceiptData
import com.techradicle.expensetracker.domain.repository.ReceiptDetailsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReceiptDetailsRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    firebaseFirestore: FirebaseFirestore,
    storage: FirebaseStorage
) : ReceiptDetailsRepository {

    var userCollection = firebaseFirestore.collection(RECEIPTS).document(auth.currentUser!!.uid)
    private val receiptRef = userCollection.collection(RECEIPTS)
    var storageRef = storage.reference

    override fun getReceipt(receiptId: String) = callbackFlow {
        val receiptIdRef = receiptRef.document(receiptId)
        val snapshotListener = receiptIdRef.addSnapshotListener { snapshot, e ->
            val receiptResponse = if (snapshot != null) {
                val receipt = snapshot.toObject(ReceiptData::class.java)
                Response.Success(receipt)
            } else {
                Response.Failure(e)
            }
            trySend(receiptResponse)
        }
        awaitClose { snapshotListener.remove() }
    }

    override suspend fun updateReceipt(
        receiptId: String,
        updateReceiptData: UpdateReceiptData
    ): Response<Boolean> {
        return try {
            receiptRef.document(receiptId)
                .update(
                    mapOf(
                        FirebaseConstants.STORE_NAME to updateReceiptData.storeName,
                        FirebaseConstants.TOTAL to updateReceiptData.amount,
                        FirebaseConstants.DATE to updateReceiptData.date,
                        FirebaseConstants.CARD_NO to updateReceiptData.cardNum,
                        FirebaseConstants.MODIFIED_ON to FieldValue.serverTimestamp()
                    )
                ).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun deleteReceipt(receiptId: String, fileName: String): Response<Boolean> {
        return try {
            receiptRef.document(receiptId).delete().await()
            if (fileName.isNotEmpty())
                storageRef.child(fileName).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }

    }
}