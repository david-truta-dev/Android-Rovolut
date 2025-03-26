package com.tdavidc.dev.data.source.remote.service

import com.tdavidc.dev.data.source.model.SessionData
import retrofit2.Response
import retrofit2.http.GET

interface AuthService {

//    @POST("users/authenticate")
//    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("users/authenticate")
    suspend fun login(): Response<SessionData>

}