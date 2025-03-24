package com.tdavidc.dev.viewmodels.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

sealed interface UIModel<T> {
    data class Success<T>(val data: T) : UIModel<T>
    data class Error<T>(val exception: Throwable) : UIModel<T>
    class Loading<T> : UIModel<T>
}
