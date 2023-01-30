package com.techradicle.expensetracker.components.icons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ThumbImage(
    url: String,
    width: Dp,
    height: Dp,
    padding: Dp = 0.dp,
    tint: ColorFilter? = null
) {
    AsyncImage(
        modifier = Modifier
            .width(width)
            .height(height)
            .padding(padding),
        colorFilter = tint,
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null
    )
}