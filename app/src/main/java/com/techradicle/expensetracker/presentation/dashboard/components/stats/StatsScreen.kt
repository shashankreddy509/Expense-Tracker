package com.techradicle.expensetracker.presentation.dashboard.components.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatsScreen(padding: PaddingValues) {

    Column(modifier = Modifier.padding(padding)) {
        Box(modifier = Modifier.height(150.dp))
        LazyColumn() {
            item {
                StatsRowItem("46","Others","304.48")
                StatsRowItem("42","Food","279.12")
                StatsRowItem("7","Household","48.64")
                StatsRowItem("5","Beauty","30.03")
            }
        }
    }
}