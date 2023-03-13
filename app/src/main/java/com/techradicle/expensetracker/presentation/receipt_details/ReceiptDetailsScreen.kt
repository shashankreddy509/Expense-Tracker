package com.techradicle.expensetracker.presentation.receipt_details

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.components.ExpenseAlertDialog
import com.techradicle.expensetracker.components.ProgressBar
import com.techradicle.expensetracker.components.TopAppBarWithActions
import com.techradicle.expensetracker.core.AppConstants.ALERT
import com.techradicle.expensetracker.core.AppConstants.DELETE_RECEIPT
import com.techradicle.expensetracker.core.AppConstants.EXPENSES
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.presentation.receipt_details.components.ReceiptDetailContent

@ExperimentalMaterial3Api
@Composable
fun ReceiptDetailsScreen(
    receiptId: String,
    navigateBack: () -> Unit,
    onEditIconClick: (receiptId: String) -> Unit,
    viewModel: ReceiptDetailsViewModel = hiltViewModel()
) {
    val openDialog = remember { mutableStateOf(false) }
    val fileName = remember { mutableStateOf("") }

    LaunchedEffect(key1 = receiptId) {
        viewModel.getReceipt(receiptId = receiptId)
    }

    Scaffold(
        topBar = {
            TopAppBarWithActions(
                title = EXPENSES,
                navigateBack = navigateBack,
                onEditIconClick = { onEditIconClick(receiptId) },
                receiptId = receiptId,
                onDeleteIconClick = {
                    openDialog.value = true
                }
            )
        },
        content = { padding ->
            ReceiptDetailContent(
                receiptID = receiptId,
                padding = padding,
                name = fileName
            )
            if (openDialog.value) {
                ExpenseAlertDialog(
                    openDialog = openDialog,
                    title = ALERT,
                    text = DELETE_RECEIPT,
                    onConfirm = {
                        viewModel.deleteReceipt(receiptId = receiptId, fileName = fileName.value)
                        openDialog.value = false
                    }
                )
            }
        }
    )

    when (val deleteReceiptResponse = viewModel.deleteReceiptResponse) {
        is Response.Failure -> LaunchedEffect(Unit) {
            print(deleteReceiptResponse.e)
        }
        is Response.Loading -> ProgressBar()
        is Response.Success -> deleteReceiptResponse.data?.let { deleted ->
            LaunchedEffect(key1 = deleted) {
                if (deleted)
                    navigateBack()
            }
        }
    }
}