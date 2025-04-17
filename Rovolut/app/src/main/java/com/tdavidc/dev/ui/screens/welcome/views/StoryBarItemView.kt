package com.tdavidc.dev.ui.screens.welcome.views

import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdavidc.dev.ui.theme.AppTheme


@Composable
fun StoryBarItemView(
    progress: Float,
    modifier: Modifier = Modifier,
    isBackgroundDark: Boolean = false,
) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier
            .height(2.dp),
        gapSize = (-0.5).dp,
        trackColor = if (isBackgroundDark) Color.Gray else Color.Gray,
        color = if (isBackgroundDark) Color.White else Color.Black,
        drawStopIndicator = {}
    )
}

@Preview
@Composable
fun StoryBarItemPreview() {
    AppTheme {
        StoryBarItemView(0.3f, isBackgroundDark = false)
    }
}