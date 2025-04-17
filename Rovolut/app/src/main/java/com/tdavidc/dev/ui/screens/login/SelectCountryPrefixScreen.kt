package com.tdavidc.dev.ui.screens.login

import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.views.appbar.AppBarLazyScrollableScreen

@Composable
fun SelectCountryPrefixScreen() {
    AppBarLazyScrollableScreen(
        stringResource(R.string.select_prefix_title),
        hasCloseInsteadOfBackBtn = true,
        headerContent = {

        }
    ) {
        items(countryPrefixes) {
            Text("${it.prefix} - ${it.countryName}")
        }
    }
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
)

data class CountryPhonePrefix(
    val prefix: String,
    val countryName: String,
    val countryFlagUrl: String
)