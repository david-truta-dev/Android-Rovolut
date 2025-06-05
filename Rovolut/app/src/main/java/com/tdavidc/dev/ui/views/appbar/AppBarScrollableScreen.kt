package com.tdavidc.dev.ui.views.appbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tdavidc.dev.ui.theme.AppTheme

@Composable
fun AppBarScrollableScreen(
    title: String,
    modifier: Modifier = Modifier,
    headerContent: @Composable () -> Unit = {},
    onBackClicked: (() -> Unit) = {},
    onTrailingButtonClicked: (() -> Unit) = {},
    trailingIcon: Painter? = null,
    content: @Composable () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var topAppBarHeight by remember { mutableIntStateOf(0) }
    var headerHeight by remember { mutableIntStateOf(0) }

    val progress by remember {
        derivedStateOf {
            if (headerHeight > 0) {
                (scrollState.value / headerHeight.toFloat()).coerceIn(0f, 1f)
            } else 0f
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AppBarTop(
            onBackClicked = onBackClicked,
            title = title,
            scrollProgress = progress,
            onTrailingButtonClicked = onTrailingButtonClicked,
            trailingIcon = trailingIcon,
            modifier = Modifier
                .zIndex(1f)
                .onGloballyPositioned { coordinates ->
                    topAppBarHeight = coordinates.size.height
                }
        )
        Column(
            Modifier
                .pointerInput(Unit) { }
                .verticalScroll(state = scrollState)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(with(LocalDensity.current) {
                topAppBarHeight.toDp()
            }))
            AppBarHeader(
                title = title,
                scrollProgress = { progress * 2.2f },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .onGloballyPositioned { coordinates ->
                        headerHeight = coordinates.size.height
                    }
            ) {
                headerContent()
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun AppBarScrollablePreview() {
    AppTheme {
        AppBarScrollableScreen(title = "Login")
    }
}