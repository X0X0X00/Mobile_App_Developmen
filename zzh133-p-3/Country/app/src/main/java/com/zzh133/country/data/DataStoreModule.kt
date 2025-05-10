package com.zzh133.country.data


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

// 1. 创建 DataStore 实例
val Context.dataStore by preferencesDataStore("settings")

object SettingsDataStore {
    private val DATA_SOURCE_KEY = stringPreferencesKey("data_source")
    private val LANGUAGE_KEY = stringPreferencesKey("language")
    private val THEME_KEY = stringPreferencesKey("theme")
    private val TEMP_UNIT_KEY = stringPreferencesKey("temperature_unit")

    // 2. 保存设置
    suspend fun saveDataSource(context: Context, value: String) {
        context.dataStore.edit { settings ->
            settings[DATA_SOURCE_KEY] = value
        }
    }
    suspend fun saveLanguage(context: Context, value: String) {
        context.dataStore.edit { settings ->
            settings[LANGUAGE_KEY] = value
        }
    }
    suspend fun saveTheme(context: Context, value: String) {
        context.dataStore.edit { settings ->
            settings[THEME_KEY] = value
        }
    }
    suspend fun saveTemperatureUnit(context: Context, value: String) {
        context.dataStore.edit { settings ->
            settings[TEMP_UNIT_KEY] = value
        }
    }

    // 3. 读取设置
    fun getDataSourceAsync(context: Context): Flow<String> =
        context.dataStore.data.map { it[DATA_SOURCE_KEY] ?: "API" }

    fun getDataSource(context: Context): String = runBlocking {
        context.dataStore.data.map { it[DATA_SOURCE_KEY] ?: "API" }.first()
    }

    // Language
    fun getLanguageAsync(context: Context): Flow<String> =
        context.dataStore.data.map { it[LANGUAGE_KEY] ?: "English" }

    fun getLanguage(context: Context): String = runBlocking {
        context.dataStore.data.map { it[LANGUAGE_KEY] ?: "English" }.first()
    }

    // Theme
    fun getThemeAsync(context: Context): Flow<String> =
        context.dataStore.data.map { it[THEME_KEY] ?: "Auto" }

    fun getTheme(context: Context): String = runBlocking {
        context.dataStore.data.map { it[THEME_KEY] ?: "Auto" }.first()
    }

    // Temperature Unit
    fun getTemperatureUnitAsync(context: Context): Flow<String> =
        context.dataStore.data.map { it[TEMP_UNIT_KEY] ?: "°C" }

    fun getTemperatureUnit(context: Context): String = runBlocking {
        context.dataStore.data.map { it[TEMP_UNIT_KEY] ?: "°C" }.first()
    }
}