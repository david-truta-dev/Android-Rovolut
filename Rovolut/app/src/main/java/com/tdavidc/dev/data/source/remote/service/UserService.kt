package com.tdavidc.dev.data.source.remote.service

import com.tdavidc.dev.data.source.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface UserService {

    @GET("users/me")
    fun getUser(): Single<User>

}