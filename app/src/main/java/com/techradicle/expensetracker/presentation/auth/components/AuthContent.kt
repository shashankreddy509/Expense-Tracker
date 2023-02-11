package com.techradicle.expensetracker.presentation.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.techradicle.expensetracker.R

@Composable
fun AuthContent(
    padding: PaddingValues,
    onTapSignIn: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement= Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_expenses), contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .fillMaxHeight()
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.BottomCenter
    ) {
        SignInButton(onClick = onTapSignIn)
    }
}