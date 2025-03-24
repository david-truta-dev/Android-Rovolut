package com.tdavidc.dev.data.repositories

import com.tdavidc.dev.data.sources.remote.models.LoginResponse
import com.tdavidc.dev.data.sources.remote.services.AuthService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService
) {
    fun login(countryCode: String, phoneNumber: String): Single<Response<LoginResponse>> =
        authService.login().subscribeOn(Schedulers.io()).flatMap {
            //TODO: save response in local storage
            Single.just(it)
        }
}