package com.techradicle.expensetracker.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.FirebaseAuth
import com.techradicle.expensetracker.core.AppConstants.NO_VALUE
import com.techradicle.expensetracker.core.AppConstants.RECEIPT_ID
import com.techradicle.expensetracker.presentation.auth.AuthScreen
import com.techradicle.expensetracker.presentation.dashboard.DashboardScreen
import com.techradicle.expensetracker.presentation.edit_receipt.EditReceiptDetails
import com.techradicle.expensetracker.presentation.manual_entry.ReceiptManualEntryScreen
import com.techradicle.expensetracker.presentation.receipt_details.ReceiptDetailsScreen

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@Composable
fun NavGraph(
    navController: NavHostController,
    auth: FirebaseAuth
) {
    val direction = remember(navController) { Direction(navController) }

    AnimatedNavHost(
        navController = navController,
        startDestination = if (auth.currentUser != null) Screen.DashboardScreen.route else Screen.AuthScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = Screen.AuthScreen.route
        ) {
            AuthScreen(
                navigateToHomeScreen = {
                    direction.navigateToHomeScreen()
                }
            )
        }
        composable(route = Screen.DashboardScreen.route) {
            DashboardScreen(
                navigateToReceiptDetailsScreen = { receiptId ->
                    direction.navigateToReceiptDetailsScreen(receiptId)
                },
                navigateToAuthScreen = direction.navigateToAuthScreen,
                navigateToReceiptManualScreen = direction.navigateToManualEntryReceipt
            )
        }
        composable(route = "${Screen.ReceiptDetailsScreen.route}/{$RECEIPT_ID}",
            arguments = listOf(
                navArgument(RECEIPT_ID) {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val receiptId = backStackEntry.arguments?.getString(RECEIPT_ID) ?: NO_VALUE
            ReceiptDetailsScreen(
                receiptId = receiptId,
                navigateBack = { direction.navigateBack() },
                onEditIconClick = { id ->
                    direction.navigateToReceiptEditScreen(id)
                }
            )
        }
        composable(route = "${Screen.ReceiptEditScreen.route}/{$RECEIPT_ID}",
            arguments = listOf(
                navArgument(RECEIPT_ID) {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val receiptId = backStackEntry.arguments?.getString(RECEIPT_ID) ?: NO_VALUE
            EditReceiptDetails(
                receiptId = receiptId,
                navigateBack = { direction.navigateBack() }
            )
        }
        composable(route = Screen.ReceiptManualEntryScreen.route) {
            ReceiptManualEntryScreen(
                navigateBack = { direction.navigateBack() }
            )
        }
    }
}