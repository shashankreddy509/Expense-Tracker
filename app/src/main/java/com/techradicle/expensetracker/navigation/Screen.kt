package com.techradicle.expensetracker.navigation

import com.techradicle.expensetracker.core.AppConstants.AUTH_SCREEN
import com.techradicle.expensetracker.core.AppConstants.DASHBOARD_SCREEN
import com.techradicle.expensetracker.core.AppConstants.EDIT_RECEIPT_DETAILS_SCREEN
import com.techradicle.expensetracker.core.AppConstants.RECEIPT_DETAILS_SCREEN

sealed class Screen(val route: String) {
    //Auth Screen
    object AuthScreen : Screen(AUTH_SCREEN)

    //Main/Dashboard Screen
    object DashboardScreen : Screen(DASHBOARD_SCREEN)

    //Receipt Details Screen
    object ReceiptDetailsScreen : Screen(RECEIPT_DETAILS_SCREEN)

    object ReceiptEditScreen : Screen(EDIT_RECEIPT_DETAILS_SCREEN)
}