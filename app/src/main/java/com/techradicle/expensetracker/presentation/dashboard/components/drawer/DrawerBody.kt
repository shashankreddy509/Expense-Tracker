package com.techradicle.expensetracker.presentation.dashboard.components.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.core.Utils.Companion.items
import com.techradicle.expensetracker.presentation.dashboard.DashboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.techradicle.expensetracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerBody(
    viewModel: DashboardViewModel = hiltViewModel(),
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
) {
    items.forEach { drawerItem ->
        NavigationDrawerItem(
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
            icon = {
                Icon(imageVector = drawerItem.icon, contentDescription = drawerItem.contentDescription)
            },
            label = { Text(text = drawerItem.name) },
            selected = drawerItem == viewModel.selectedItem,
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                    viewModel.selectedItem = drawerItem
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = colorResource(id = R.color.primary_dark),
                unselectedContainerColor = colorResource(id = R.color.primary),
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            ),
            shape = ShapeDefaults.ExtraSmall
        )
    }
}