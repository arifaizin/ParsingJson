package com.dicoding.parsingjson.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.parsingjson.data.UserRepository
import com.dicoding.parsingjson.data.database.UserDatabase
import com.dicoding.parsingjson.data.network.ApiConfig
import com.dicoding.parsingjson.ui.setting.SettingPreferences
import com.dicoding.parsingjson.ui.setting.dataStore

object Injection {

    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getDatabase(context)
        val dao = database.userDao()
        val pref = SettingPreferences.getInstance(context.dataStore)
        return UserRepository.getInstance(apiService, dao, pref)
    }
}