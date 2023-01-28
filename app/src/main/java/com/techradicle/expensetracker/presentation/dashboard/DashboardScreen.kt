package com.techradicle.expensetracker.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.techradicle.expensetracker.R
import com.techradicle.expensetracker.components.AuthTopBar

@ExperimentalMaterial3Api
@Composable
fun DashboardScreen() {
    Scaffold(
        topBar = {
            AuthTopBar(id = R.string.app_name)
        },
        content = { padding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {
                Text(text = "Dashboard Screen")
            }
        }
    )
}