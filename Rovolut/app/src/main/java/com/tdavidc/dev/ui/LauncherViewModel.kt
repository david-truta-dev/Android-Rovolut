package com.tdavidc.dev.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.tdavidc.dev.data.repository.auth.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(authRepository: IAuthRepository) : ViewModel() {

    val hasActiveSession: LiveData<Boolean> by lazy {
        authRepository.getSessionData().asLiveData().map { sessionData -> sessionData != null }
    }

}