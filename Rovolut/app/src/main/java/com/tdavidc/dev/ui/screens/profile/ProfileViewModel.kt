package com.tdavidc.dev.ui.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdavidc.dev.data.repository.AuthRepository
import com.tdavidc.dev.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepository: AuthRepository): BaseViewModel() {

    private val _logoutResponse by lazy { MutableLiveData<Boolean>() }
    val logoutResponse: LiveData<Boolean> = _logoutResponse

    fun logout() {
        viewModelScope.launch {
            authRepository.clearSessionData()
            _logoutResponse.value = true
        }
    }

}