package com.example.mysubmission.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

private val Context.prefDataStore by preferencesDataStore("setting")
class SettingPreferences constructor(context: Context) {

        private val settingsDataStore = context.prefDataStore
        private val themeKEY = booleanPreferencesKey("theme_setting")

        fun getThemeSetting(): Flow<Boolean> =
            settingsDataStore.data.map { preferences ->
                preferences[themeKEY] ?: false
            }

        suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
            settingsDataStore.edit { preferences ->
                preferences[themeKEY] = isDarkModeActive
            }
        }
    }