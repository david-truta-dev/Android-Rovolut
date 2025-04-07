package com.tdavidc.dev.ui.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdavidc.dev.data.repository.auth.IAuthRepository
import com.tdavidc.dev.data.repository.user.IUserRepository
import com.tdavidc.dev.data.source.model.User
import com.tdavidc.dev.ui.common.base.BaseRxViewModel
import com.tdavidc.dev.ui.common.base.BaseViewModel
import com.tdavidc.dev.ui.common.base.UIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: IUserRepository,
    private val authRepository: IAuthRepository
) : BaseRxViewModel() {

    private val _logoutResponse by lazy { MutableLiveData<Boolean>() }
    val logoutResponse: LiveData<Boolean> = _logoutResponse

    private val _user by lazy { MutableLiveData<UIModel<User>>() }
    val user: LiveData<UIModel<User>> = _user

    init {
        getUser()
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.clearSessionData()
            _logoutResponse.value = true
        }
    }

    fun getUser() {
        compositeDisposable.add(
            userRepository.getUserData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _user.value = UIModel.Loading()
                }
                .subscribe(
                    {
                        _user.value = UIModel.Success(it)
                    },
                    {
                        _user.value = UIModel.Error(it)
                        it.printStackTrace()
                    }
                )
        )
    }

}