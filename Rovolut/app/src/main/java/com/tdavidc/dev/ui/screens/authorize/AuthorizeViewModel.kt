package com.tdavidc.dev.ui.screens.authorize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdavidc.dev.ui.common.base.BaseViewModel
import com.tdavidc.dev.ui.common.base.UIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthorizeViewModel : BaseViewModel() {

    private val _passcodeLastAction by lazy { MutableLiveData<PasscodeLastStatus>() }

    private val _passcode by lazy { MutableLiveData("") }

    private val _isAuthorized by lazy { MutableLiveData<UIModel<AuthorizationStatus>>() }

    // OUTPUT: ------------
    val passcodeLastAction: LiveData<PasscodeLastStatus> = _passcodeLastAction

    val isAuthorized: LiveData<UIModel<AuthorizationStatus>> = _isAuthorized

    // INPUT: --------------

    fun startBiometricAuth() {
        //TODO: check if user allowed biometric, if yes:
        _isAuthorized.value = UIModel.Success(AuthorizationStatus.SHOW_BIOMETRIC)
        //TODO: Otherwise,
        // _isAuthorized.value = UIModel.Success(AuthorizationStatus.SHOW_KEYBOARD)
    }

    fun biometricAuthSuccess() {
        _isAuthorized.value = UIModel.Success(AuthorizationStatus.AUTHORIZED)
    }

    fun biometricAuthCancelled() {
        _isAuthorized.value = UIModel.Success(AuthorizationStatus.SHOW_KEYBOARD)
    }

    fun digitClicked(digit: String) {
        _passcode.value?.let {
            _passcode.value = it + digit
            _passcodeLastAction.value = PasscodeLastStatus.ADDED(it.length, it)
            if (_passcode.value?.length == PASSCODE_LENGTH) {
                validatePasscode()
            }
        }

    }

    fun backSpaceClicked() {
        _passcode.value?.let {
            val newVal = it.dropLast(1)
            _passcode.value = newVal
            _passcodeLastAction.value =
                PasscodeLastStatus.REMOVED(newVal.length, newVal)
        }
    }

    // OTHER: -------------

    private fun validatePasscode() {
        viewModelScope.launch {
            //TODO: get the actual passcode from the local storage
            _isAuthorized.value = UIModel.Loading()
            delay(300) // add fake delay for now
            if (_passcode.value == "123456") {
                _isAuthorized.value = UIModel.Success(AuthorizationStatus.AUTHORIZED)
            } else {
                _isAuthorized.value = UIModel.Success(AuthorizationStatus.REJECTED)
                _passcode.value = ""
            }
        }
    }

    companion object {
        const val PASSCODE_LENGTH = 6
    }
}

enum class AuthorizationStatus {
    AUTHORIZED,
    REJECTED,
    SHOW_BIOMETRIC,
    SHOW_KEYBOARD,
}

sealed class PasscodeLastStatus(val passcode: String) {
    class ADDED(val index: Int, passcode: String) : PasscodeLastStatus(passcode)
    class REMOVED(val index: Int, passcode: String) :
        PasscodeLastStatus(passcode)
}
