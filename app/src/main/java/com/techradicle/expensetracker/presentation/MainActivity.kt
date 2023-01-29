package com.techradicle.expensetracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.techradicle.expensetracker.navigation.NavGraph
import com.techradicle.expensetracker.navigation.Screen
import com.techradicle.expensetracker.presentation.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            navController = rememberAnimatedNavController()
            NavGraph(
                navController = navController,
                auth = auth
            )
            checkAuthState()
        }
    }

    private fun checkAuthState() {
        if (viewModel.isAuthenticated) {
            navigateToHomeScreen()
        }
    }

    private fun navigateToHomeScreen() = navController.navigate(Screen.DashboardScreen.route)
}