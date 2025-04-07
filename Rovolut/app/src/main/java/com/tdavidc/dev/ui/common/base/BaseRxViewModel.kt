package com.tdavidc.dev.ui.common.base

import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseRxViewModel: BaseViewModel() {
    protected val compositeDisposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}