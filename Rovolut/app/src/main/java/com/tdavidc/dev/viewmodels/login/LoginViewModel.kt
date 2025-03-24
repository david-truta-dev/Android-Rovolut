package com.tdavidc.dev.viewmodels.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tdavidc.dev.data.repositories.AuthRepository
import com.tdavidc.dev.viewmodels.base.BaseViewModel
import com.tdavidc.dev.viewmodels.base.UIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _loginResponse by lazy { MutableLiveData<UIModel<Exception?>>() }
    val loginResponse: LiveData<UIModel<Exception?>> = _loginResponse

    fun login(countryCode: String, phoneNumber: String) {
        authRepository.login(countryCode, phoneNumber)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _loginResponse.value = UIModel.Loading() }
            .subscribe(
                { response ->
                    if (response.isSuccessful) {
                        _loginResponse.value = UIModel.Success(data = null)
                    } else {
                        _loginResponse.value = UIModel.Error(exception = Exception("Error"))
                    }
                }, { error ->
                    _loginResponse.value = UIModel.Error(exception = error)
                }
            )
            .addTo(disposables)
    }

}