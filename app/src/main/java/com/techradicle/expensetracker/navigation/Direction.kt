package com.techradicle.expensetracker.navigation

import androidx.navigation.NavController

class Direction(navController: NavController) {

//    val navigateToAuthScreen: () -> Unit = {
//        navController.popBackStack()
//        navController.navigate(Screen.AuthScreen.route)
//    }

    val navigateToHomeScreen: () -> Unit = {
        navController.navigate(Screen.DashboardScreen.route)
    }
}