package com.techradicle.expensetracker.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.techradicle.expensetracker.R

@ExperimentalMaterial3Api
@Composable
fun AuthTopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        colors = getTopBarColors()
    )
}

@Composable
@ExperimentalMaterial3Api
fun getTopBarColors() = TopAppBarDefaults.smallTopAppBarColors(
    containerColor = colorResource(R.color.primary),
    navigationIconContentColor = Color.White,
    actionIconContentColor = Color.White
)