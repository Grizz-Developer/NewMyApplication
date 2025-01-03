package com.guit.edu.myapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object DataStoreUtil { // 使用 object 关键字创建单例对象

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    val TOKEN = stringPreferencesKey("token")
    val REMEMBER_PASSWORD = booleanPreferencesKey("remember_password")
    val SAVED_PASSWORD = stringPreferencesKey("saved_password")
    val SAVED_USERNAME = stringPreferencesKey("saved_username")

    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[TOKEN] = token
        }
    }

    fun getToken(context: Context): Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN] ?: ""
        }
    suspend fun saveRememberPassword(context: Context, remember: Boolean) {
        context.dataStore.edit { settings ->
            settings[REMEMBER_PASSWORD] = remember
        }
    }
    fun getRememberPassword(context: Context): Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[REMEMBER_PASSWORD] ?: false
        }
    suspend fun savePassword(context: Context, password: String) {
        context.dataStore.edit { settings ->
            settings[SAVED_PASSWORD] = password
        }
    }

    fun getPassword(context: Context): Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[SAVED_PASSWORD] ?: ""
        }
    suspend fun saveUsername(context: Context, username: String) {
        context.dataStore.edit { settings ->
            settings[SAVED_USERNAME] = username
        }
    }

    fun getUsername(context: Context): Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[SAVED_USERNAME] ?: ""
        }
}
