package com.techradicle.expensetracker.core

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.paging.LoadState
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.*
import com.techradicle.expensetracker.core.AppConstants.FIREBASE_FUNCTION_ANNOTATE_IMAGE
import com.techradicle.expensetracker.core.AppConstants.TAG
import java.io.ByteArrayOutputStream

class Utils {

    companion object {
        fun print(e: Exception?) {
            Log.e(TAG, e?.message ?: e.toString())
        }

        fun print(errorState: LoadState.Error) {
            val error = errorState.error
            Log.d(TAG, error.message ?: error.toString())
        }

        fun annotateImage(requestJson: String, functions: FirebaseFunctions): Task<JsonElement> {
            return functions
                .getHttpsCallable(FIREBASE_FUNCTION_ANNOTATE_IMAGE)
                .call(requestJson)
                .continueWith { task ->
                    // This continuation runs on either success or failure, but if the task
                    // has failed then result will throw an Exception which will be
                    // propagated down.
                    val result = task.result?.data
                    JsonParser.parseString(Gson().toJson(result))
                }
        }

        fun scaleBitmapDown(bitmap: Bitmap, maxDimension: Int): Bitmap {
            val originalWidth = bitmap.width
            val originalHeight = bitmap.height
            var resizedWidth = maxDimension
            var resizedHeight = maxDimension
            if (originalHeight > originalWidth) {
                resizedHeight = maxDimension
                resizedWidth =
                    (resizedHeight * originalWidth.toFloat() / originalHeight.toFloat()).toInt()
            } else if (originalWidth > originalHeight) {
                resizedWidth = maxDimension
                resizedHeight =
                    (resizedWidth * originalHeight.toFloat() / originalWidth.toFloat()).toInt()
            } else if (originalHeight == originalWidth) {
                resizedHeight = maxDimension
                resizedWidth = maxDimension
            }
            return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false)
        }

        fun getRequestData(bitmap: Bitmap): String {
            scaleBitmapDown(bitmap, 640)
            // Convert bitmap to base64 encoded string
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            val base64encoded = Base64.encodeToString(imageBytes, Base64.NO_WRAP)

            // Create json request to cloud vision
            val request = JsonObject()
            // Add image to request
            val image = JsonObject()
            image.add("content", JsonPrimitive(base64encoded))
            request.add("image", image)
            //Add features to the request
            val feature = JsonObject()
            feature.add("type", JsonPrimitive("TEXT_DETECTION"))
            // Alternatively, for DOCUMENT_TEXT_DETECTION:
            // feature.add("type", JsonPrimitive("DOCUMENT_TEXT_DETECTION"))
            val features = JsonArray()
            features.add(feature)
            request.add("features", features)

            val imageContext = JsonObject()
            val languageHints = JsonArray()
            languageHints.add("en")
            imageContext.add("languageHints", languageHints)
            request.add("imageContext", imageContext)
            return request.toString()
        }
    }
}