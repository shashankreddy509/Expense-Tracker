package com.techradicle.expensetracker.presentation.dashboard

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.techradicle.expensetracker.BuildConfig
import com.techradicle.expensetracker.R
import com.techradicle.expensetracker.components.AuthTopBar
import com.techradicle.expensetracker.core.AppConstants.TAG
import com.techradicle.expensetracker.core.Utils
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

@ExperimentalMaterial3Api
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageData by remember { mutableStateOf("") }
    val context = LocalContext.current
    val functions: FirebaseFunctions = Firebase.functions

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )

    fun getBitmapImage(
        context: Context,
        imageUri: Uri,
        functions: FirebaseFunctions
    ): String {
        var bitmap: Bitmap =
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        bitmap = Utils.scaleBitmapDown(bitmap, 640)

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

        Utils.annotateImage(request.toString(), functions)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e(TAG, "Failure")
                    // Task failed with an exception
                    // ...
                    task.exception!!.printStackTrace()
                    imageData = task.exception!!.message!!
                    Log.e(TAG, task.exception!!.message!!)
                } else {
                    // Task completed successfully
                    // ...
                    Log.e(TAG, "Success")
                    val annotation =
                        task.result!!.asJsonArray[0].asJsonObject["fullTextAnnotation"].asJsonObject
                    imageData = annotation["text"].asString
                    var pageText = ""
                    val paras = mutableListOf<String>()
                    for (page in annotation["pages"].asJsonArray) {
                        for (block in page.asJsonObject["blocks"].asJsonArray) {
                            var blockText = ""
                            for (para in block.asJsonObject["paragraphs"].asJsonArray) {
                                var paraText = ""
                                for (word in para.asJsonObject["words"].asJsonArray) {
                                    var wordText = ""
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
                    Log.e(TAG, paras.joinToString())
                }
            }

        return imageData
    }

    Scaffold(
        topBar = {
            AuthTopBar(id = R.string.app_name)
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (hasImage && imageUri != null) {
                    getBitmapImage(context, imageUri!!, functions)
                    Text(text = imageData)
                    AsyncImage(
                        model = imageUri,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(200.dp),
                        contentDescription = "Selected image",
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    hasImage = false
                    val uri = getTmpFileUri(context)
                    imageUri = uri
                    cameraLauncher.launch(imageUri)
                },
                containerColor = colorResource(id = R.color.primary_dark),
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50))
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    )
}

fun getTmpFileUri(context: Context): Uri {
    val tmpFile =
        File.createTempFile("tmp_image_file_${Date().time}", ".png", context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        tmpFile
    )
}