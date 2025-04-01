package com.tdavidc.dev.data.source.remote.model

data class LoginRequest(
    val countryCode: String,
    val phoneNumber: String
)