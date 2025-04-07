package com.tdavidc.dev.data.source.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val firstName: String,
    val lastName: String,
    val userTag: String,
    val passcode: String,
)