package com.example.plantcare.Pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore("user_prefs")
class UserPreferences(private val context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
    }

    suspend fun saveUserId(userId: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    suspend fun getUserId(): String? {
        val preferences = dataStore.data.first()
        return preferences[USER_ID_KEY]
    }
}
