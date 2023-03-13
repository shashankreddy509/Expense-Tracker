package com.techradicle.expensetracker.presentation.accounts.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.techradicle.expensetracker.core.AppConstants

@Composable
fun AccountsContent() {
    Column {
        AccountsRow(AppConstants.CASH, "")
        AccountsRow(AppConstants.CARD, "")
        AccountsRow(AppConstants.BANK, "")
    }
}