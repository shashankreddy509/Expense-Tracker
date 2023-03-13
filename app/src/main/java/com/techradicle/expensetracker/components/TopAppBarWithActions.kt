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
    navigateBack: () -> Unit,
    onEditIconClick: (receiptId: String) -> Unit,
    onDeleteIconClick:  () -> Unit,
    receiptId: String
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.Black
            )
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = GO_BACK)
            }
        },
        actions = {
            TopBarActions(onEditIconClick, receiptId, onDeleteIconClick)
        },
        colors = getTopBarColors()
    )

}