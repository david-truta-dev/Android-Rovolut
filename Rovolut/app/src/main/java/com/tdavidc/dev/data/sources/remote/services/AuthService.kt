package com.tdavidc.dev.data.sources.remote.services

import com.tdavidc.dev.data.sources.remote.models.LoginResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

interface AuthService {

//    @POST("users/authenticate")
//    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("users/authenticate")
    fun login(): Single<Response<LoginResponse>>

}