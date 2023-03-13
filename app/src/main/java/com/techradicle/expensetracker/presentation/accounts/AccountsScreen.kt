package com.techradicle.expensetracker.presentation.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techradicle.expensetracker.core.AppConstants
import com.techradicle.expensetracker.presentation.accounts.components.AccountsContent
import com.techradicle.expensetracker.presentation.dashboard.components.newlayout.Header

@ExperimentalMaterial3Api
@Composable
fun AccountsScreen(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(padding)
    ) {
        val totalExpenses = 4500.0
        val totalIncome = 162.27
        val total = 4337.73
        Header(
            totalExpenses, totalIncome, total,
            AppConstants.ASSETS,
            AppConstants.LIABILITIES,
            AppConstants.TOTAL
        )
        AccountsContent()
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun AccountsScreenPreview() {
    val padding = PaddingValues(8.dp)
    AccountsScreen(padding)
}