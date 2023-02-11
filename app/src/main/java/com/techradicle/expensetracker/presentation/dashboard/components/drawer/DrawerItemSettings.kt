package com.techradicle.expensetracker.presentation.dashboard.components.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.components.ProgressBar
import com.techradicle.expensetracker.core.AppConstants.SAVE
import com.techradicle.expensetracker.core.Utils
import com.techradicle.expensetracker.core.Utils.Companion.items
import com.techradicle.expensetracker.domain.model.Response
import com.techradicle.expensetracker.domain.model.SettingValues
import com.techradicle.expensetracker.presentation.dashboard.DashboardViewModel

@ExperimentalMaterial3Api
@Composable
fun DrawerItemSettings(
    viewModel: DashboardViewModel = hiltViewModel(),
    padding: PaddingValues
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getSettingValues()
    }

    val maxMonthSpent = remember { mutableStateOf("") }
    val maxReceiptSpent = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Max spent on monthly basics")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = maxMonthSpent.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { value ->
                    maxMonthSpent.value = value
                }
            )

            Text(text = "max spent on a single receipt")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = maxReceiptSpent.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { value ->
                    maxReceiptSpent.value = value
                }
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            OutlinedButton(
                onClick = {
                    viewModel.saveSettingsValues(
                        SettingValues(
                            maxAmountPerReceipt = maxReceiptSpent.value.toDouble(),
                            maxMonthAmount = maxMonthSpent.value.toDouble()
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = SAVE)
            }
        }
    }

    when (val saveSettings = viewModel.settingValuesResponse) {
        is Response.Failure -> LaunchedEffect(key1 = Unit) { Utils.print(saveSettings.e) }
        is Response.Loading -> ProgressBar()
        is Response.Success -> saveSettings.data?.let { status ->
            if (status) {
                viewModel.selectedItem = items[0]
                viewModel.settingValuesResponse = Response.Success(false)
            }
        }
    }

    when (val saveSettings = viewModel.settingDataResponse) {
        is Response.Failure -> LaunchedEffect(key1 = Unit) { Utils.print(saveSettings.e) }
        is Response.Loading -> ProgressBar()
        is Response.Success -> saveSettings.data?.let { data ->
            maxReceiptSpent.value = (data.maxAmountPerReceipt ?: 0.0).toString()
            maxMonthSpent.value = (data.maxMonthAmount ?: 0.0).toString()
            viewModel.settingDataResponse = Response.Success(null)
        }
    }
}