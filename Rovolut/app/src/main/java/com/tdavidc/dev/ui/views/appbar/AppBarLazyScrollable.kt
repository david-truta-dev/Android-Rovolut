package com.tdavidc.dev.ui.views.appbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tdavidc.dev.ui.theme.AppTheme

@Composable
fun AppBarLazyScrollable(
    title: String,
    modifier: Modifier = Modifier,
    headerContent: @Composable () -> Unit = {},
    onBackClicked: (() -> Unit) = {},
    onTrailingButtonClicked: (() -> Unit) = {},
    trailingIcon: Painter? = null,
    content: LazyListScope.() -> Unit
) {
    val listState = rememberLazyListState()
    var topAppBarHeight by remember { mutableIntStateOf(0) }
    var headerHeight by remember { mutableIntStateOf(0) }

    val progress by remember {
        derivedStateOf {
            val scrollOffset = if (listState.firstVisibleItemIndex == 0) {
                listState.firstVisibleItemScrollOffset
            } else {
                headerHeight
            }
            (scrollOffset / headerHeight.toFloat()).coerceIn(0f, 1f)
        }
    }

    Box(modifier = modifier) {
        AppBarTop(
            onBackClicked = onBackClicked,
            title = title,
            scrollProgress = progress,
            onTrailingButtonClicked = onTrailingButtonClicked,
            trailingIcon = trailingIcon,
            modifier = Modifier
                .zIndex(progress - 0.5f)
                .onGloballyPositioned {
                    topAppBarHeight = it.size.height
                }
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(top = with(LocalDensity.current) { topAppBarHeight.toDp() })
        ) {
            item {
                AppBarHeader(
                    title = title,
                    scrollProgress = progress * 2.2f,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .onGloballyPositioned {
                            headerHeight = it.size.height
                        }
                ) {
                    headerContent()
                }
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
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
fun AppBarLazyScrollablePreview() {
    AppTheme {
        AppBarLazyScrollable(title = "Login") {
            items(10) {
                Text("Item")
            }
        }
    }
}