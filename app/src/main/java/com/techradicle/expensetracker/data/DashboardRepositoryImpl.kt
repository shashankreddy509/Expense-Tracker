package com.techradicle.expensetracker.data

import android.net.Uri
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storageMetadata
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.techradicle.expensetracker.core.AppConstants.FIREBASE_FUNCTION_ANNOTATE_IMAGE
import com.techradicle.expensetracker.core.AppConstants.NO_VALUE
import com.techradicle.expensetracker.core.AppConstants.TAG
import com.techradicle.expensetracker.core.FirebaseConstants.CREATED_AT
import com.techradicle.expensetracker.core.FirebaseConstants.IMAGE_DATA
import com.techradicle.expensetracker.core.FirebaseConstants.IMAGE_URL
import com.techradicle.expensetracker.core.FirebaseConstants.PAGE_SIZE
import com.techradicle.expensetracker.core.FirebaseConstants.RECEIPTS
import com.techradicle.expensetracker.core.FirebaseConstants.UID
import com.techradicle.expensetracker.domain.model.ImageUploadData
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.model.Response.Failure
import com.techradicle.expensetracker.domain.model.Response.Success
import com.techradicle.expensetracker.domain.model.User
import com.techradicle.expensetracker.domain.repository.DashboardRepository
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor(
    val auth: FirebaseAuth,
    val storage: FirebaseStorage,
    val firestore: FirebaseFirestore,
    val functions: FirebaseFunctions,
    private val config: PagingConfig
) : DashboardRepository {

    var storageRef = storage.reference
    var uidFirestoreRef = firestore.collection(RECEIPTS).document()

    override val user = User(
        uid = auth.currentUser?.uid ?: NO_VALUE,
        photoUrl = auth.currentUser?.photoUrl.toString(),
        displayName = auth.currentUser?.displayName ?: NO_VALUE,
        email = auth.currentUser?.email ?: NO_VALUE
    )

    override suspend fun uploadImageToStorage(
        uri: Uri,
        requestJson: String
    ): Response<ImageUploadData> {
        return try {
            val metadata = storageMetadata {
                contentType = "image/png"
            }
            val uidRef: StorageReference =
                storageRef.child("${auth.currentUser!!.uid}/${Date().time}.png")
            val downloadUrl = uidRef
                .putFile(uri, metadata).await()
                .storage.downloadUrl.await()
            val imageData = getTextFrom(requestJson)
            Success(ImageUploadData(downloadUrl, imageData))
        } catch (e: Exception) {
            Failure(e)
        }
    }

    private suspend fun getTextFrom(requestJson: String): String {
        val result = functions
            .getHttpsCallable(FIREBASE_FUNCTION_ANNOTATE_IMAGE)
            .call(requestJson).await()
            .data
        val parseString = JsonParser.parseString(Gson().toJson(result))
        val annotation = parseString.asJsonArray[0].asJsonObject["fullTextAnnotation"].asJsonObject

        var pageText = ""
        val paras = mutableListOf<String>()
        for (page in annotation["pages"].asJsonArray) {
            for (block in page.asJsonObject["blocks"].asJsonArray) {
                var blockText = ""
                for (para in block.asJsonObject["paragraphs"].asJsonArray) {
                    var paraText = ""
                    for (word in para.asJsonObject["words"].asJsonArray) {
                        var wordText = ""
                        Log.e(TAG, word.toString())
                        for (symbol in word.asJsonObject["symbols"].asJsonArray) {
                            wordText += symbol.asJsonObject["text"].asString
                        }
                        paraText = String.format("%s%s ", paraText, wordText)
                    }
                    paras.add(paraText)
                    blockText += paraText
                }
                pageText += blockText
            }
        }
        return pageText
    }

    override suspend fun addImageToDatabase(imageData: ImageUploadData): Response<Boolean> {
        return try {
            uidFirestoreRef.set(
                mapOf(
                    IMAGE_URL to imageData.imageUrl,
                    CREATED_AT to FieldValue.serverTimestamp(),
                    IMAGE_DATA to imageData.imageData,
                    UID to auth.currentUser!!.uid
                )
            ).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun getReceiptsFromFirestore() = Pager(
        config = config
    ) {
        ReceiptsPagingSource(
            query = firestore.collection(RECEIPTS)
                .whereEqualTo(UID, user.uid)
                .limit(PAGE_SIZE)
        )
    }.flow

    fun getData() {
        firestore.collection(RECEIPTS).whereEqualTo("uid","eK9TuyNhcVdwMUACGTBKeHT8D4H3").get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.e(TAG,document.documents.size.toString())
                    Log.e(TAG, "DocumentSnapshot data: ${document.documents}")
                } else {
                    Log.d(TAG, "No such document")
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}