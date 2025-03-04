package com.tdavidc.dev.viewmodels.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tdavidc.dev.viewmodels.base.BaseViewModel

class WelcomeViewModel : BaseViewModel() {

    private val _welcomeScreens by lazy { MutableLiveData<ArrayList<WelcomeScreen>>() }
 
    private val _currentScreenIndex by lazy { MutableLiveData(0) }

    // OUTPUT: ------------
    val currentScreenIndex: LiveData<Int> = _currentScreenIndex

    val welcomeScreens: LiveData<ArrayList<WelcomeScreen>> = _welcomeScreens

    // INPUT: --------------

    fun setWelcomeScreens(screens: ArrayList<WelcomeScreen>) {
        _welcomeScreens.value = screens
    }

    fun goToNextScreen() {
        if (_currentScreenIndex.value == _welcomeScreens.value?.size?.minus(1)) {
            _currentScreenIndex.value = 0
            return
        }
        _currentScreenIndex.value = _currentScreenIndex.value?.plus(1)
    }

    fun goToPreviousScreen() {
        if (_currentScreenIndex.value == 0) return
        _currentScreenIndex.value = _currentScreenIndex.value?.minus(1)
    }
}

data class WelcomeScreen(
    val title: Int,
    val description: Int? = null,
    val background: Int,
    val dark: Boolean,
)