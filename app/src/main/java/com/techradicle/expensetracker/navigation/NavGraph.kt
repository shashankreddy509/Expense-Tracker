package com.techradicle.expensetracker.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.FirebaseAuth
import com.techradicle.expensetracker.presentation.auth.AuthScreen
import com.techradicle.expensetracker.presentation.dashboard.DashboardScreen

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
            DashboardScreen()
        }
    }
}