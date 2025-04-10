package com.tdavidc.dev.ui.screens.launcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.tdavidc.dev.data.repository.auth.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(authRepository: IAuthRepository) : ViewModel() {
    private val _continueAfterSplashAnimation by lazy { MutableLiveData(false) }
    val continueAfterSplashAnimation: LiveData<Boolean> = _continueAfterSplashAnimation

    private val doesHaveSession by lazy {
        authRepository.getSessionData().asLiveData().map { sessionData -> sessionData != null }
    }

    val hasActiveSession: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        hasActiveSession.addSource(continueAfterSplashAnimation) { value1 ->
            val value2 = doesHaveSession.value
            if (value2 != null && value1 != null) {
                hasActiveSession.value = value2 == true
            }
        }

        hasActiveSession.addSource(doesHaveSession) { value2 ->
            val value1 = continueAfterSplashAnimation.value
            if (value1 != null && value2 != null) {
                hasActiveSession.value = value2
            }
        }
    }

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