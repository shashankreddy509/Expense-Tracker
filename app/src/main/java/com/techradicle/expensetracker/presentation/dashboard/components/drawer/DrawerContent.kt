package com.techradicle.expensetracker.presentation.dashboard.components.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.techradicle.expensetracker.R
import com.techradicle.expensetracker.domain.model.User
import kotlinx.coroutines.CoroutineScope

@ExperimentalMaterial3Api
@Composable
fun DrawerContent(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    user: User
) {

    ModalDrawerSheet(
        drawerContainerColor = colorResource(id = R.color.primary)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            DrawerHeader(user = user)
            DrawerDivider()
            DrawerBody(
                drawerState = drawerState,
                coroutineScope = coroutineScope
            )
        }
    }
}