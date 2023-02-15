package com.techradicle.expensetracker.data

import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storageMetadata
import com.techradicle.expensetracker.core.AppConstants.NO_VALUE
import com.techradicle.expensetracker.core.FirebaseConstants.CARD_NO
import com.techradicle.expensetracker.core.FirebaseConstants.CREATED_AT
import com.techradicle.expensetracker.core.FirebaseConstants.DATE
import com.techradicle.expensetracker.core.FirebaseConstants.FILE_NAME
import com.techradicle.expensetracker.core.FirebaseConstants.ID
import com.techradicle.expensetracker.core.FirebaseConstants.IMAGE_DATA
import com.techradicle.expensetracker.core.FirebaseConstants.IMAGE_URL
import com.techradicle.expensetracker.core.FirebaseConstants.ITEMS
import com.techradicle.expensetracker.core.FirebaseConstants.MAX_AMOUNT_FOR_MONTH
import com.techradicle.expensetracker.core.FirebaseConstants.MAX_AMOUNT_PER_RECEIPT
import com.techradicle.expensetracker.core.FirebaseConstants.PAGE_SIZE
import com.techradicle.expensetracker.core.FirebaseConstants.RECEIPTS
import com.techradicle.expensetracker.core.FirebaseConstants.SETTINGS
import com.techradicle.expensetracker.core.FirebaseConstants.STORE_NAME
import com.techradicle.expensetracker.core.FirebaseConstants.TIME
import com.techradicle.expensetracker.core.FirebaseConstants.TOTAL
import com.techradicle.expensetracker.core.FirebaseConstants.UID
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.data.remote.OcrApi
import com.techradicle.expensetracker.domain.model.*
import com.techradicle.expensetracker.domain.model.Response.Failure
import com.techradicle.expensetracker.domain.model.Response.Success
import com.techradicle.expensetracker.domain.model.ocrdata.OcrData
import com.techradicle.expensetracker.domain.repository.DashboardRepository
import com.techradicle.expensetracker.domain.repository.SettingsValuesResponse
import com.techradicle.expensetracker.domain.repository.SignOutResponse
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    storage: FirebaseStorage,
    firestore: FirebaseFirestore,
    private val config: PagingConfig,
    private val api: OcrApi
) : DashboardRepository {

    var storageRef = storage.reference
    var userCollection = firestore.collection(RECEIPTS).document(auth.currentUser!!.uid)
    var firestorCollection = userCollection.collection(RECEIPTS)
    var settingsCollection = userCollection.collection(SETTINGS)

    override val user = User(
        uid = auth.currentUser?.uid ?: NO_VALUE,
        photoUrl = auth.currentUser?.photoUrl.toString(),
        displayName = auth.currentUser?.displayName ?: NO_VALUE,
        email = auth.currentUser?.email ?: NO_VALUE
    )

    override suspend fun uploadImageToStorage(
        uri: Uri, filePath: String
    ): Response<ImageUploadData> {
        return try {
            val ocrData = getImageResponse(filePath)
            ocrData.data?.let {
                val imageFile = "${auth.currentUser!!.uid}/${Date().time}.png"
                val metadata = storageMetadata { contentType = "image/png" }
                val uidRef: StorageReference =
                    storageRef.child(imageFile)
                val downloadUrl = uidRef
                    .putFile(uri, metadata).await()
                    .storage.downloadUrl.await()
                Success(ImageUploadData(downloadUrl, imageData = it, imageName = imageFile))
            } ?: Failure(ocrData.e)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun addImageToDatabase(imageData: ImageUploadData): Response<Boolean> {
        return try {
            val id = firestorCollection.document().id
            val receipt = imageData.imageData.receipts[0]
            firestorCollection.document(id).set(
                mapOf(
                    IMAGE_URL to imageData.imageUrl,
                    CREATED_AT to FieldValue.serverTimestamp(),
                    IMAGE_DATA to imageData.imageData,
                    UID to auth.currentUser!!.uid,
                    ID to id,
                    STORE_NAME to receipt.merchant_name,
                    TOTAL to receipt.total,
                    DATE to receipt.date,
                    TIME to receipt.time,
                    ITEMS to receipt.items,
                    CARD_NO to receipt.credit_card_number,
                    FILE_NAME to imageData.imageName
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
            query = firestorCollection
                .whereGreaterThanOrEqualTo(DATE, Utils.getFirstDateOfTheMonth())
                .whereLessThanOrEqualTo(DATE, Utils.getLastDateOfTheMonth())
                .orderBy(DATE, Query.Direction.DESCENDING)
                .limit(PAGE_SIZE)
        )
    }.flow

    override suspend fun signOut(): SignOutResponse {
        return try {
            oneTapClient.signOut().await()
            auth.signOut()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun saveSettingValues(settingsValues: SettingValues): SettingsValuesResponse {
        return try {
            settingsCollection.document(user.uid).set(
                mapOf(
                    MAX_AMOUNT_PER_RECEIPT to settingsValues.maxAmountPerReceipt,
                    MAX_AMOUNT_FOR_MONTH to settingsValues.maxMonthAmount
                )
            ).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getSettingValues(): Response<SettingsData> {
        return try {
            val settingData = settingsCollection.document(user.uid).get().await()
            Success(settingData.toObject(SettingsData::class.java))
        } catch (e: Exception) {
            Failure(e)
        }
    }

    private suspend fun getImageResponse(fileUrl: String): OcrData {
        return try {
            val file = File(fileUrl)
            val fbody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val createFormData = MultipartBody.Part.createFormData("file", file.name, fbody)
            val api_key: RequestBody = "TEST".toRequestBody("text/plain".toMediaTypeOrNull())
            val recognizer: RequestBody = "auto".toRequestBody("text/plain".toMediaTypeOrNull())
            val response =
                api.getOcrDataForImage(
                    file = createFormData,
                    api_key = api_key,
                    recognizer = recognizer
                )
            OcrData(response, null)
        } catch (e: Exception) {
            e.printStackTrace()
            OcrData(null, e)
        }
    }
}