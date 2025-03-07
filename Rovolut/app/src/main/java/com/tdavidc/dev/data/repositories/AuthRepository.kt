package com.tdavidc.dev.data.repositories

import com.tdavidc.dev.data.sources.remote.services.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService
) {

}