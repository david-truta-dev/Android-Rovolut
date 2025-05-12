package com.tdavidc.dev.ui.screens.welcome.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdavidc.dev.ui.theme.AppTheme


@Composable
fun StoryBarView(
    count: Int,
    currentPosition: Int,
    currentProgress: () -> Float,
    modifier: Modifier = Modifier,
    isBackgroundDark: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 0 until count) {
            val progress: () -> Float = when {
                i < currentPosition -> {
                    { 1f }
                }

                i > currentPosition -> {
                    { 0f }
                }

                else -> currentProgress
            }
            StoryBarItemView(
                progress = progress,
                isBackgroundDark = isBackgroundDark,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun StoryBarPreview() {
    AppTheme {
        StoryBarView(6, 2, { 0.3f })
    }
}