package com.techradicle.expensetracker.data.remote

import com.techradicle.expensetracker.domain.model.ocr.ImageDataList
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OcrApi {

    @Multipart
    @POST("receipt")
    suspend fun getOcrDataForImage(
        @Part file: MultipartBody.Part,
        @Part("api_key") api_key: RequestBody,
        @Part("recognizer") recognizer: RequestBody,
    ): ImageDataList
}