package com.dicoding.parsingjson.data

import com.dicoding.parsingjson.data.database.FavoriteUser
import com.dicoding.parsingjson.data.database.UserDao
import com.dicoding.parsingjson.data.model.ItemsItem
import com.dicoding.parsingjson.data.network.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
) {
    suspend fun addFavorite(item: FavoriteUser) {
        userDao.insert(item)
    }

    suspend fun deleteFavorite(item: FavoriteUser) {
        userDao.delete(item)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: UserDao,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, newsDao)
            }.also { instance = it }
    }
}