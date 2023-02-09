package com.techradicle.expensetracker.navigation

import androidx.navigation.NavController

class Direction(navController: NavController) {

    val navigateToAuthScreen: () -> Unit = {
        navController.popBackStack()
        navController.navigate(Screen.AuthScreen.route)
    }

    val navigateToHomeScreen: () -> Unit = {
        navController.navigate(Screen.DashboardScreen.route)
    }

    val navigateToReceiptDetailsScreen: (String) -> Unit = { receiptId ->
        navController.navigate("${Screen.ReceiptDetailsScreen.route}/$receiptId")
    }

    val navigateToReceiptEditScreen: (String) -> Unit = { receiptId ->
        navController.navigate("${Screen.ReceiptEditScreen.route}/$receiptId")
    }

    val navigateToManualEntryReceipt: () -> Unit = {
        navController.navigate(Screen.ReceiptManualEntryScreen.route)
    }


    val navigateBack: () -> Unit = {
        navController.navigateUp()
    }
}