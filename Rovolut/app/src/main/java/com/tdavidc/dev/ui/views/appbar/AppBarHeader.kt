package com.tdavidc.dev.ui.views.appbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdavidc.dev.ui.theme.AppTheme


@Composable
internal fun AppBarHeader(
    title: String,
    modifier: Modifier = Modifier,
    scrollProgress: Float = 0f,
    bottomContent: @Composable () -> Unit = { Spacer(modifier = Modifier.height(0.dp)) }
) {
    // fades from 0 to 1, ensuring value is between 0 and 1
    val safeScrollProgress = 1f - scrollProgress.coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                alpha = safeScrollProgress
            }) {
        Text(
            title,
            modifier = Modifier.padding(vertical = 4.dp),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
        bottomContent()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = false, backgroundColor = 0xFFFFFFFF
)
@Composable
fun TopScreenHeaderPreview() {
    AppTheme {
        AppBarHeader(
            title = "Preview",
            scrollProgress = 0.5f
        )
    }
}