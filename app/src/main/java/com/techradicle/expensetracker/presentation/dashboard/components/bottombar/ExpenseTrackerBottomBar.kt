package com.techradicle.expensetracker.presentation.dashboard.components.bottombar

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.core.AppConstants
import com.techradicle.expensetracker.core.Utils.Companion.items
import com.techradicle.expensetracker.presentation.dashboard.DashboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun ExpenseTrackerBottomBar(
    selectedItem: MutableState<Int>,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    NavigationBar(
        tonalElevation = 5.dp
    ) {
        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(selected = selectedItem.value == index, onClick = {
                selectedItem.value = index
                if (index == 1) {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                } else {
                    viewModel.selectedItem = items[index]
                }
            },
                label = {
                    Text(
                        text = bottomNavItem.name,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
                    Icon(
                        imageVector = bottomNavItem.icon,
                        contentDescription = "${bottomNavItem.name} Icon",
                    )
                })
        }
    }
}

val bottomNavItems = listOf(
    BottomNavItem(
        name = AppConstants.HOME,
        route = AppConstants.HOME,
        icon = Icons.Rounded.Home,
    ),
    BottomNavItem(
        name = AppConstants.ADD_RECEIPT,
        route = AppConstants.ADD_RECEIPT,
        icon = Icons.Rounded.AddCircle,
    ),
    BottomNavItem(
        name = AppConstants.SETTINGS,
        route = AppConstants.SETTINGS,
        icon = Icons.Rounded.Settings,
    ),
    BottomNavItem(
        name = AppConstants.SIGN_OUT,
        route = AppConstants.SIGN_OUT,
        icon = Icons.Rounded.ExitToApp,
    ),
)