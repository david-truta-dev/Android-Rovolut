package com.tdavidc.dev.ui.screens.authenticate.selectprefix

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tdavidc.dev.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class SelectPhonePrefixViewModel @Inject constructor() : ViewModel() {
    private val _allCountries = arrayListOf<CountryPhonePrefix>()

    init {
        _allCountries.addAll(countryPrefixes)
    }

    var searchedText by mutableStateOf("")
        private set

    val filteredCountries: List<CountryPhonePrefix>
        get() = if (searchedText.isBlank()) {
            _allCountries
        } else {
            _allCountries.filter {
                it.countryName.contains(searchedText, ignoreCase = true) ||
                        it.prefix.contains(searchedText)
            }
        }

    fun onSearchTextChanged(newValue: String) {
        searchedText = newValue
    }

    fun clearSearch() {
        searchedText = ""
    }
}

@Parcelize
data class CountryPhonePrefix(
    val prefix: String,
    val countryName: String,
    val countryFlagResId: Int
) : Parcelable

val countryPrefixes = arrayListOf(
    CountryPhonePrefix(
        prefix = "+40",
        countryName = "Romania",
        countryFlagResId = R.drawable.ic_flag_ro
    ),
    CountryPhonePrefix(
        prefix = "+353",
        countryName = "Ireland",
        countryFlagResId = R.drawable.ic_flag_ir
    ),
    CountryPhonePrefix(
        prefix = "+44",
        countryName = "United Kingdom",
        countryFlagResId = R.drawable.ic_flag_uk
    ),
    CountryPhonePrefix(
        prefix = "+30",
        countryName = "Greece",
        countryFlagResId = R.drawable.ic_flag_gr
    ),
    CountryPhonePrefix(
        prefix = "+34",
        countryName = "Spain",
        countryFlagResId = R.drawable.ic_flag_es
    ),
    CountryPhonePrefix(
        prefix = "+33",
        countryName = "France",
        countryFlagResId = R.drawable.ic_flag_fr
    ),
    CountryPhonePrefix(
        prefix = "+36",
        countryName = "Hungary",
        countryFlagResId = R.drawable.ic_flag_hu
    ),

    CountryPhonePrefix(
        prefix = "+39",
        countryName = "Italy",
        countryFlagResId = R.drawable.ic_flag_it
    ),
    CountryPhonePrefix(
        prefix = "+49",
        countryName = "Germany",
        countryFlagResId = R.drawable.ic_flag_de
    ),
    CountryPhonePrefix(
        prefix = "+400",
        countryName = "Romania",
        countryFlagResId = R.drawable.ic_flag_ro
    ),
    CountryPhonePrefix(
        prefix = "+350",
        countryName = "Ireland",
        countryFlagResId = R.drawable.ic_flag_ir
    ),
    CountryPhonePrefix(
        prefix = "+440",
        countryName = "United Kingdom",
        countryFlagResId = R.drawable.ic_flag_uk
    ),
    CountryPhonePrefix(
        prefix = "+300",
        countryName = "Greece",
        countryFlagResId = R.drawable.ic_flag_gr
    ),
    CountryPhonePrefix(
        prefix = "+340",
        countryName = "Spain",
        countryFlagResId = R.drawable.ic_flag_es
    ),
    CountryPhonePrefix(
        prefix = "+330",
        countryName = "France",
        countryFlagResId = R.drawable.ic_flag_fr
    ),
    CountryPhonePrefix(
        prefix = "+360",
        countryName = "Hungary",
        countryFlagResId = R.drawable.ic_flag_hu
    ),

    CountryPhonePrefix(
        prefix = "+390",
        countryName = "Italy",
        countryFlagResId = R.drawable.ic_flag_it
    ),
    CountryPhonePrefix(
        prefix = "+490",
        countryName = "Germany",
        countryFlagResId = R.drawable.ic_flag_de
    ),
    CountryPhonePrefix(
        prefix = "+401",
        countryName = "Romania",
        countryFlagResId = R.drawable.ic_flag_ro
    ),
    CountryPhonePrefix(
        prefix = "+351",
        countryName = "Ireland",
        countryFlagResId = R.drawable.ic_flag_ir
    ),
    CountryPhonePrefix(
        prefix = "+441",
        countryName = "United Kingdom",
        countryFlagResId = R.drawable.ic_flag_uk
    ),
    CountryPhonePrefix(
        prefix = "+301",
        countryName = "Greece",
        countryFlagResId = R.drawable.ic_flag_gr
    ),
    CountryPhonePrefix(
        prefix = "+341",
        countryName = "Spain",
        countryFlagResId = R.drawable.ic_flag_es
    ),
    CountryPhonePrefix(
        prefix = "+331",
        countryName = "France",
        countryFlagResId = R.drawable.ic_flag_fr
    ),
    CountryPhonePrefix(
        prefix = "+361",
        countryName = "Hungary",
        countryFlagResId = R.drawable.ic_flag_hu
    ),

    CountryPhonePrefix(
        prefix = "+391",
        countryName = "Italy",
        countryFlagResId = R.drawable.ic_flag_it
    ),
    CountryPhonePrefix(
        prefix = "+491",
        countryName = "Germany",
        countryFlagResId = R.drawable.ic_flag_de
    ),
)
