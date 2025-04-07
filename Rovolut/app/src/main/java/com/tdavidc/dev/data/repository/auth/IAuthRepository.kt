package com.tdavidc.dev.data.repository.auth

import com.tdavidc.dev.data.source.model.SessionData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IAuthRepository {
    suspend fun login(countryCode: String, phoneNumber: String): Response<SessionData>

    fun didAllowBiometrics(): Flow<Boolean>

    suspend fun allowBiometrics(value: Boolean)

    fun getSessionData(): Flow<SessionData?>

    suspend fun clearSessionData()
}