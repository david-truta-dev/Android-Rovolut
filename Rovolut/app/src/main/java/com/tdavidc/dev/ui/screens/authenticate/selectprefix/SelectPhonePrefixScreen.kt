package com.tdavidc.dev.ui.screens.authenticate.selectprefix

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.views.buttons.ButtonSize
import com.tdavidc.dev.ui.views.buttons.CircleImageButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPhonePrefixScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onCountrySelected: (CountryPhonePrefix) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(stringResource(R.string.select_prefix_title))
                        val collapsedFraction = scrollBehavior.state.collapsedFraction
                        if (collapsedFraction < 0.45f) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(modifier = Modifier.padding(end = 12.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clickable {

                                        }
                                        .background(
                                            color = MaterialTheme.colorScheme.surface,
                                            shape = CircleShape
                                        )
                                        .clip(CircleShape)
                                        .padding(horizontal = 6.dp)
                                        .fillMaxWidth()
                                        .height(38.dp)
                                ) {
                                    Image(
                                        painterResource(R.drawable.ic_search),
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                                    )
                                    Text(
                                        stringResource(R.string.select_prefix_search_placeholder),
                                        style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                },
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
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 12.dp)
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
            ) {
                items(countryPrefixes) {
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
                            painterResource(R.drawable.ic_flag_ro),
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SelectPhonePrefixScreenPreview() {
    SelectPhonePrefixScreen()
}

val countryPrefixes = listOf(
    CountryPhonePrefix(
        prefix = "+40",
        countryName = "Romania",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+353",
        countryName = "Ireland",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+44",
        countryName = "United Kingdom",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+34",
        countryName = "Spain",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagUrl = ""
    ),
)

data class CountryPhonePrefix(
    val prefix: String,
    val countryName: String,
    val countryFlagUrl: String
)