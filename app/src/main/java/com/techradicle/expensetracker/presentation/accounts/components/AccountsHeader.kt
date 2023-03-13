package com.techradicle.expensetracker.presentation.accounts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techradicle.expensetracker.R
import com.techradicle.expensetracker.core.AppConstants

@Composable
fun AccountsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp, top = 8.dp, bottom = 8.dp, start = 32.dp)
        ) {
            Text(
                text = AppConstants.ASSETS,
                color = colorResource(id = R.color.dim_gray),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
            )
            Text(
                text = "$ 4500",
                color = colorResource(id = R.color.steel_blue),
                fontSize = 12.sp,
            )
        }
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = AppConstants.LIABILITIES,
                color = colorResource(id = R.color.dim_gray),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
            )
            Text(
                text = "$ 162.27",
                color = colorResource(id = R.color.pastel_red),
                fontSize = 12.sp,
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 32.dp, top = 8.dp, bottom = 8.dp, start = 8.dp)
        ) {
            Text(
                text = AppConstants.TOTAL,
                color = colorResource(id = R.color.dim_gray),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Text(
                text = "$ 4337.73",
                color = colorResource(id = R.color.gray),
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}