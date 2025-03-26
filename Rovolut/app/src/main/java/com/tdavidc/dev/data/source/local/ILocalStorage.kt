package com.tdavidc.dev.data.source.local

import com.tdavidc.dev.data.source.model.SessionData
import kotlinx.coroutines.flow.Flow

interface ILocalStorage {

    fun getSessionData(): Flow<SessionData?>
    suspend fun setSessionData(value: SessionData?)

//    suspend fun getSessionData(): Flow<SessionData?>
//    suspend fun setSessionData(value: SessionData)
}
