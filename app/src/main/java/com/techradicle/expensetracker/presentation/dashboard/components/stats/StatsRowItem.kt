package com.techradicle.expensetracker.presentation.dashboard.components.stats

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatsRowItem(
    percentage: String,
    category: String,
    amount: String
) {
    Row(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "$percentage%",
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = category,
            modifier = Modifier.weight(0.6f)
        )
        Text(
            text = "$ $amount",
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.3f)
        )
    }
}

@Preview
@Composable
fun StatsRowItemPreview() {
    StatsRowItem("30", "Others", "350.5")
}