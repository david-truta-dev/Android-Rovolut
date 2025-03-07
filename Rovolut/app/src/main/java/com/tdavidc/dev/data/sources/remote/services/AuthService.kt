package com.tdavidc.dev.data.sources.remote.services

import com.tdavidc.dev.data.sources.remote.models.LoginRequest
import com.tdavidc.dev.data.sources.remote.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}