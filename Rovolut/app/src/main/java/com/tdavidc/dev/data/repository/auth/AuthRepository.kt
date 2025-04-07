package com.tdavidc.dev.data.repository.auth

import com.tdavidc.dev.data.source.local.ILocalStorage
import com.tdavidc.dev.data.source.model.SessionData
import com.tdavidc.dev.data.source.remote.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val localStorage: ILocalStorage
) : IAuthRepository {
    override suspend fun login(countryCode: String, phoneNumber: String): Response<SessionData> =
        withContext(Dispatchers.IO) {
            val response = authService.login()
            if (response.isSuccessful && response.body() != null) {
                localStorage.setSessionData(response.body()!!)
            }
            return@withContext response
        }

    override fun didAllowBiometrics(): Flow<Boolean> {
        return localStorage.didAllowBiometrics()
    }

    override suspend fun allowBiometrics(value: Boolean) = withContext(Dispatchers.IO) {
        localStorage.allowBiometrics(value)
    }

    override fun getSessionData(): Flow<SessionData?> {
        return localStorage.getSessionData()
    }

    override suspend fun clearSessionData() = withContext(Dispatchers.IO) {
        localStorage.setSessionData(null)
    }
}