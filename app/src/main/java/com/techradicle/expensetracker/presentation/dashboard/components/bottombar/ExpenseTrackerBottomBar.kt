package com.techradicle.expensetracker.presentation.dashboard.components.bottombar

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.More
import androidx.compose.material.icons.rounded.StackedBarChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techradicle.expensetracker.R
import com.techradicle.expensetracker.core.AppConstants
import com.techradicle.expensetracker.core.Utils.Companion.items
import com.techradicle.expensetracker.presentation.dashboard.DashboardViewModel
import kotlinx.coroutines.CoroutineScope

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
        tonalElevation = 5.dp,
        containerColor = colorResource(id = R.color.primary_light)
    ) {
        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(selected = selectedItem.value == index, onClick = {
                selectedItem.value = index
//                if (index == 1) {
//                    coroutineScope.launch {
//                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
//                            bottomSheetScaffoldState.bottomSheetState.expand()
//                        }
//                    }
//                } else {
//                }
                viewModel.selectedItem = items[index]
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
        name = AppConstants.STATS,
        route = AppConstants.STATS,
        icon = Icons.Rounded.StackedBarChart,
    ),
    BottomNavItem(
        name = AppConstants.ACCOUNTS,
        route = AppConstants.ACCOUNTS,
        icon = Icons.Rounded.AccountCircle,
    ),
    BottomNavItem(
        name = AppConstants.MORE,
        route = AppConstants.MORE,
        icon = Icons.Rounded.More,
    ),
)