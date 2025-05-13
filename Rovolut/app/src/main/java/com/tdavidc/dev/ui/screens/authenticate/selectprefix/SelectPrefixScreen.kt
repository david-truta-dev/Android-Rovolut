package com.tdavidc.dev.ui.screens.authenticate.selectprefix

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.views.buttons.ButtonSize
import com.tdavidc.dev.ui.views.buttons.CircleImageButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPrefixScreen(
    modifier: Modifier = Modifier,
    viewModel: SelectPhonePrefixViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
    onCountrySelected: (CountryPhonePrefix) -> Unit = {}
) {
    var searchMode by rememberSaveable { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        canScroll = { !searchMode }
    )

    val searchedText = viewModel.searchedText
    val filteredCountries = viewModel.filteredCountries

    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    LaunchedEffect(listState.isScrollInProgress) {
        keyboardController?.hide()
    }


    Scaffold(
        modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AnimatedVisibility(
                searchMode,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 16.dp)
                ) {
                    SearchBar(
                        leadingIcon = painterResource(R.drawable.ic_back_arrow),
                        onLeadingClick = {
                            searchMode = false
                            viewModel.clearSearch()
                        },
                        value = searchedText,
                        onValueChange = viewModel::onSearchTextChanged,
                        placeholder = stringResource(R.string.select_prefix_search_placeholder),
                        requestFocus = true,
                        keyboardController = keyboardController
                    )
                }
            }

            AnimatedVisibility(
                !searchMode,
                exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
                enter = expandVertically(expandFrom = Alignment.Top) + fadeIn()
            ) {
                LargeTopAppBar(
                    title = {
                        Column {
                            Text(stringResource(R.string.select_prefix_title))
                            AnimatedVisibility(visible = scrollBehavior.state.collapsedFraction < 0.45f) {
                                Box(
                                    modifier = Modifier
                                        .padding(end = 14.dp)
                                        .padding(top = 12.dp)
                                ) {
                                    SearchBar(
                                        onClick = {
                                            searchMode = true
                                        }
                                    )
                                }
                            }
                        }
                    },
                    collapsedHeight = 34.dp,
                    navigationIcon = {
                        CircleImageButton(
                            painterResource(R.drawable.ic_close),
                            onClick = onBackClicked,
                            circleButtonSize = ButtonSize.Medium,
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        content = { padding ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 12.dp)
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                items(filteredCountries, contentType = { item -> "country" }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                            .clickable {
                                onCountrySelected(it)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(it.countryFlagResId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                        )
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(it.countryName, style = MaterialTheme.typography.labelLarge)
                            Text(it.prefix)
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun SearchBar(
    onClick: (() -> Unit)? = null,
    leadingIcon: Painter = painterResource(R.drawable.ic_search),
    onLeadingClick: (() -> Unit)? = null,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = stringResource(R.string.select_prefix_search_placeholder),
    requestFocus: Boolean = false,
    keyboardController: SoftwareKeyboardController? = null
) {
    val focusRequester = remember { FocusRequester() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(CircleShape) // Round the corners
            .background(color = MaterialTheme.colorScheme.surface) // Background color
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current
            ) { onClick?.invoke() }
            .padding(horizontal = 6.dp) // Padding around the content
            .fillMaxWidth()
            .height(38.dp) // Keep the height small
    ) {

        var imgModifier = Modifier.clip(RoundedCornerShape(4.dp))
        imgModifier =
            if (onLeadingClick != null) imgModifier.clickable { onLeadingClick() } else Modifier
        Image(
            modifier = imgModifier,
            painter = leadingIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )
        BasicTextField(
            enabled = onClick == null,
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            placeholder,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.5f
                                )
                            )
                        )
                    }
                    innerTextField()
                }
            },
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxHeight()
                .focusRequester(focusRequester)
        )

        LaunchedEffect(Unit) {
            if (requestFocus) {
                focusRequester.requestFocus()
                keyboardController?.show()
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SelectPhonePrefixScreenPreview() {
    SelectPrefixScreen()
}