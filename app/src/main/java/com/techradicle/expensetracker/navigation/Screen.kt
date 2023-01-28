package com.techradicle.expensetracker.navigation

import com.techradicle.expensetracker.core.AppConstants.AUTH_SCREEN
import com.techradicle.expensetracker.core.AppConstants.DASHBOARD_SCREEN

sealed class Screen(val route: String) {
    //Auth Screen
    object AuthScreen : Screen(AUTH_SCREEN)
    //Main/Dashboard Screen
    object DashboardScreen : Screen(DASHBOARD_SCREEN)
}