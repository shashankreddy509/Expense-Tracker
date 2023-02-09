package com.techradicle.expensetracker.presentation.manual_entry.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.core.AppConstants.ADD_RECEIPT
import com.techradicle.expensetracker.domain.model.InsertReceiptData
import com.techradicle.expensetracker.presentation.edit_receipt.components.EditRowItems
import com.techradicle.expensetracker.presentation.manual_entry.ReceiptManualEntryViewModel

@Composable
fun AddReceiptContent(
    padding: PaddingValues,
    receiptManualEntryViewModel: ReceiptManualEntryViewModel = hiltViewModel()
) {

    Column(modifier = Modifier.padding(padding)) {
        val storeName = remember { mutableStateOf("") }
        val total = remember { mutableStateOf("") }
        val date = remember { mutableStateOf("") }
        val cardNo = remember { mutableStateOf("") }

        EditRowItems("Store name: ", storeName)
        EditRowItems("Created at: ", date)
        EditRowItems("Amount: ", total, KeyboardOptions(keyboardType = KeyboardType.Decimal))
        EditRowItems("Card No: ", cardNo)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.BottomCenter
        ) {
            OutlinedButton(
                onClick = {
                    val insertReceiptData = InsertReceiptData(
                        storeName = storeName.value,
                        total = total.value.toDouble(),
                        date = date.value,
                        cardNo = cardNo.value
                    )
                    receiptManualEntryViewModel.addReceiptDataToDatabase(insertReceiptData)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = ADD_RECEIPT)
            }
        }
    }
}