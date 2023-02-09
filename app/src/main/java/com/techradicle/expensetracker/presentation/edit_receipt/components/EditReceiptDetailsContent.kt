package com.techradicle.expensetracker.presentation.edit_receipt.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.components.ExpenseAlertDialog
import com.techradicle.expensetracker.core.AppConstants.ALERT
import com.techradicle.expensetracker.core.AppConstants.TAG
import com.techradicle.expensetracker.core.AppConstants.UPDATE
import com.techradicle.expensetracker.core.AppConstants.UPDATE_RECEIPT
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.domain.model.UpdateReceiptData
import com.techradicle.expensetracker.presentation.receipt_details.ReceiptDetailsViewModel

@ExperimentalMaterial3Api
@Composable
fun EditReceiptDetailsContent(
    receiptID: String,
    padding: PaddingValues,
    receiptDetailsViewModel: ReceiptDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = receiptID) {
        receiptDetailsViewModel.getReceipt(receiptId = receiptID)
    }

    val openDialog = remember { mutableStateOf(false) }

    EditReceiptDetail { receipt ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {

            receipt.apply {
                val dateCorrect = receipt.date?.let {
                    Utils.getCorrectDate(it, receipt.createdAt!!)
                } ?: Utils.dateFormatter(receipt.createdAt!!)
                val merchantName = remember { mutableStateOf(receipt.storeName!!) }
                val dateAt = remember { mutableStateOf(dateCorrect) }
                val amount = remember { mutableStateOf(receipt.total.toString()) }
                val cardNumber = remember { mutableStateOf(receipt.cardNo ?: "0") }
                EditRowItems("Store name: ", merchantName)
                EditRowItems("Created at: ", dateAt)
                EditRowItems(
                    "Amount: ", amount,
                    KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                EditRowItems(
                    "Card No: ", cardNumber,
                    KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                if (openDialog.value) {
                    ExpenseAlertDialog(
                        openDialog = openDialog,
                        title = ALERT,
                        text = UPDATE_RECEIPT,
                        onConfirm = {
                            receiptDetailsViewModel.updateReceipt(
                                receiptId = receiptID,
                                updateReceiptData = UpdateReceiptData(
                                    storeName = merchantName.value,
                                    date = dateAt.value,
                                    amount = amount.value.toDouble(),
                                    cardNum = cardNumber.value
                                )
                            )
                            openDialog.value = false
                        }
                    )
                }
                OutlinedButton(onClick = {
                    Log.e(TAG, "Store Name: ${merchantName.value}")
                    Log.e(TAG, "dateAt : ${dateAt.value}")
                    Log.e(TAG, "amount: ${amount.value}")
                    Log.e(TAG, "cardNumber: ${cardNumber.value}")

                    openDialog.value = true
                }) {
                    Text(text = UPDATE)
                }
            }
        }
    }
}