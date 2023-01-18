package com.dicoding.parsingjson.di

import android.content.Context
import com.dicoding.parsingjson.data.UserRepository
import com.dicoding.parsingjson.data.database.UserDatabase
import com.dicoding.parsingjson.data.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getDatabase(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}