package com.tdavidc.dev.ui.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdavidc.dev.data.repository.AuthRepository
import com.tdavidc.dev.ui.viewmodel.base.BaseViewModel
import com.tdavidc.dev.ui.viewmodel.base.UIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _loginResponse by lazy { MutableLiveData<UIModel<Exception?>>() }
    val loginResponse: LiveData<UIModel<Exception?>> = _loginResponse

    fun login(countryCode: String, phoneNumber: String) {
        _loginResponse.value = UIModel.Loading()
        viewModelScope.launch {
            val response = authRepository.login(countryCode, phoneNumber)
            if (response.isSuccessful) {
                _loginResponse.value = UIModel.Success(null)
            } else {
                _loginResponse.value = UIModel.Error(Exception("Error"))
            }
        }
    }

}