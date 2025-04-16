package com.tdavidc.dev.ui.views.appbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.theme.AppTheme
import com.tdavidc.dev.ui.views.ButtonSize
import com.tdavidc.dev.ui.views.CircleImageButton


@Composable
internal fun AppBarTop(
    onBackClicked: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    scrollProgress: Float = 0f,
    onTrailingButtonClicked: () -> Unit = {},
    trailingIcon: Painter? = null,
    hasCloseInsteadOfBackBtn: Boolean = false
) {
    // fades from 0 to 1, ensuring value is between 0 and 1
    val contentAlphaProgress =
        if (scrollProgress < 0.5f) 0f
        else ((scrollProgress - 0.5f) / 0.5f).coerceIn(0f, 1f)
    // moves from offset 25dp to 0dp at progress >= 90%
    val animatedOffsetY = (25 * (1f - contentAlphaProgress)).dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = contentAlphaProgress))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleImageButton(
                painterResource(
                    if (hasCloseInsteadOfBackBtn)
                        R.drawable.ic_close
                    else
                        R.drawable.ic_back_arrow
                ),
                onClick = onBackClicked,
                circleButtonSize = ButtonSize.Medium,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                title,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 12.dp)
                    .padding(top = animatedOffsetY)
                    .graphicsLayer {
                        alpha = contentAlphaProgress
                    },
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            if (trailingIcon != null)
                CircleImageButton(
                    trailingIcon,
                    onClick = onTrailingButtonClicked,
                    circleButtonSize = ButtonSize.Medium,
                    containerColor = Color.Transparent,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = false, backgroundColor = 0xFFFFFFFF
)
@Composable
fun TopAppBarPreview() {
    AppTheme {
        AppBarTop(
            onBackClicked = {},
            title = "Preview"
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = false
)
@Composable
fun TopAppBarPreview2() {
    AppTheme {
        AppBarTop(
            onBackClicked = {},
            title = "Preview",
            scrollProgress = 0.65f,
            trailingIcon = painterResource(R.drawable.ic_chart),
            onTrailingButtonClicked = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = false
)
@Composable
fun TopAppBarPreview3() {
    AppTheme {
        AppBarTop(
            onBackClicked = {},
            title = "Preview",
            hasCloseInsteadOfBackBtn = true,
            scrollProgress = 1f,
            trailingIcon = painterResource(R.drawable.ic_chart),
            onTrailingButtonClicked = {}
        )
    }
}
