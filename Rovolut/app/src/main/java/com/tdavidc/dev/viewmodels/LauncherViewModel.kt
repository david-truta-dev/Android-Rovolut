package com.tdavidc.dev.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LauncherViewModel: ViewModel() {
    private val _continueAfterSplashAnimation by lazy { MutableLiveData(false) }
    val continueAfterSplashAnimation: LiveData<Boolean> = _continueAfterSplashAnimation

    fun viewIsCreated() {
        viewModelScope.launch {
            delay(DELAY_FOR_SPLASH_ANIMATION)
            _continueAfterSplashAnimation.value = true
        }
    }

    companion object {
        private const val DELAY_FOR_SPLASH_ANIMATION = 800L
    }
}