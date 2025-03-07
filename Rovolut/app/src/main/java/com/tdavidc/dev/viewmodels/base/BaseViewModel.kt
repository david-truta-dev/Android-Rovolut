package com.tdavidc.dev.viewmodels.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {}

sealed interface UIModel<T> {
    data class Success<T>(val data: T) : UIModel<T>
    data class Error<T>(val exception: Throwable) : UIModel<T>
    class Loading<T> : UIModel<T>
}
