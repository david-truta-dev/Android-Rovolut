package com.tdavidc.dev.data.source.local


import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.tdavidc.dev.data.source.model.SessionData
import com.tdavidc.dev.utility.encryption.CryptoData
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
        getCustomClassPreference(KEY_SESSION_DATA, SessionData.serializer(), true)


    override suspend fun setSessionData(value: SessionData?) {
        setCustomClassPreference(KEY_SESSION_DATA, SessionData.serializer(), value, true)
    }

    private companion object {
        val KEY_SESSION_DATA = byteArrayPreferencesKey("session_data")
    }

    private fun <T> getCustomClassPreference(
        key: Preferences.Key<ByteArray>,
        deserializer: DeserializationStrategy<T>,
        encrypted: Boolean = false
    ): Flow<T?> = myDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val byteArray = preferences[key]
            ?: return@map null
        if (encrypted) {
            val decryptedData = CryptoData.getInstance().decrypt(byteArray)
                ?: return@map null
            ProtoBuf.decodeFromByteArray(deserializer, decryptedData)
        } else {
            ProtoBuf.decodeFromByteArray(deserializer, byteArray)
        }
    }

    private suspend fun <T> setCustomClassPreference(
        key: Preferences.Key<ByteArray>,
        serializer: KSerializer<T>,
        value: T?,
        encrypted: Boolean = false
    ) {
        if (value == null) {
            myDataStore.edit { preferences -> preferences.remove(key) }
            return
        }
        val serializedValue = ProtoBuf.encodeToByteArray(serializer, value)
        if (encrypted) {
            val encryptedValue = CryptoData.getInstance().encrypt(serializedValue)
            myDataStore.edit { preferences -> preferences[key] = encryptedValue }
        } else {
            myDataStore.edit { preferences -> preferences[key] = serializedValue }
        }
    }
}

