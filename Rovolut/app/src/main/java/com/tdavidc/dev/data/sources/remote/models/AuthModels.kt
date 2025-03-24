package com.tdavidc.dev.data.sources.remote.models

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val accessToken: String, val refreshToken: String)