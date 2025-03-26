package com.tdavidc.dev.data.source.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionData(
    val accessToken: String,
    val refreshToken: String
)
