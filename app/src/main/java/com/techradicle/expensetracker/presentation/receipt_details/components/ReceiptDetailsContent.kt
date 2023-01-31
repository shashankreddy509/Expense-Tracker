package com.techradicle.expensetracker.presentation.receipt_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.techradicle.expensetracker.components.ShortDivider
import com.techradicle.expensetracker.presentation.receipt_details.ReceiptDetailsViewModel

@Composable
fun ReceiptDetailContent(
    receiptID: String,
    padding: PaddingValues,
    receiptDetailsViewModel: ReceiptDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = receiptID) {
        receiptDetailsViewModel.getReceipt(receiptId = receiptID)
    }

    ReceiptDetail { receipt ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                receipt.apply {
                    Box(contentAlignment = Alignment.TopEnd) {
                        //Place Image here
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = null
                        )
                    }
                    ShortDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 8.dp
                            )
                    ) {
                        Text(
                            text = createdAt.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            fontSize = 21.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}