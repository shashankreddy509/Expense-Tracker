package com.techradicle.expensetracker.data

import com.google.firebase.firestore.FirebaseFirestore
import com.techradicle.expensetracker.core.FirebaseConstants.RECEIPTS
import com.techradicle.expensetracker.domain.model.ReceiptData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.repository.ReceiptDetailsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ReceiptDetailsRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : ReceiptDetailsRepository {

    private val receiptRef = firebaseFirestore.collection(RECEIPTS)

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

}