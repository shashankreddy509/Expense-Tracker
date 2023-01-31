package com.techradicle.expensetracker.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.techradicle.expensetracker.core.AppConstants.GO_BACK

@ExperimentalMaterial3Api
@Composable
fun TopAppBarWithActions(
    title: String,
    navigateBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = GO_BACK)
            }
        },
        colors = getTopBarColors()
    )

}