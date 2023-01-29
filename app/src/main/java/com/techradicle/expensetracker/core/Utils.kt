package com.techradicle.expensetracker.core

import android.graphics.Bitmap
import android.util.Log
import androidx.paging.LoadState
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.techradicle.expensetracker.core.AppConstants.FIREBASE_FUNCTION_ANNOTATE_IMAGE
import com.techradicle.expensetracker.core.AppConstants.TAG

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
    }
}