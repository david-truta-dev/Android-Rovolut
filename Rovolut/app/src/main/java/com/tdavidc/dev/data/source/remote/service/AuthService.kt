package com.tdavidc.dev.data.source.remote.service

import com.tdavidc.dev.data.source.model.SessionData
import retrofit2.Response
import retrofit2.http.GET

interface AuthService {

    @GET("users/authenticate")
    // Should be POST, andi t should have LoginRequest() as body,
    // but I am limited by mock json server
    suspend fun login(): Response<SessionData>

}