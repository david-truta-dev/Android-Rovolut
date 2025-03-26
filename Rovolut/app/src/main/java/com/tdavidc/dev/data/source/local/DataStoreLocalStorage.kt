package com.tdavidc.dev.data.source.local


import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.tdavidc.dev.data.source.model.SessionData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("data_store_local_storage")

@OptIn(ExperimentalSerializationApi::class)
class DataStoreLocalStorage @Inject constructor(@ApplicationContext appContext: Context) :
    ILocalStorage {

    private val myDataStore = appContext.dataStore

    override fun getSessionData(): Flow<SessionData?> =
        getCustomClassPreference(KEY_SESSION_DATA, SessionData.serializer())


    override suspend fun setSessionData(value: SessionData?) {
        setCustomClassPreference(KEY_SESSION_DATA, SessionData.serializer(), value)
    }

    private companion object {
        val KEY_SESSION_DATA = byteArrayPreferencesKey("session_data")
    }

    private fun <T> getCustomClassPreference(
        key: Preferences.Key<ByteArray>,
        deserializer: DeserializationStrategy<T>
    ): Flow<T?> = myDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val byteArray = preferences[key] ?: return@map null
        ProtoBuf.decodeFromByteArray(deserializer, byteArray)
    }

    private suspend fun <T> setCustomClassPreference(
        key: Preferences.Key<ByteArray>,
        serializer: KSerializer<T>,
        value: T?
    ) {
        if (value == null) {
            myDataStore.edit { preferences -> preferences.remove(key) }
            return
        }
        val serializedValue = ProtoBuf.encodeToByteArray(serializer, value)
        myDataStore.edit { preferences -> preferences[key] = serializedValue }
    }
}

