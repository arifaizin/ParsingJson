package com.dicoding.parsingjson.data

import androidx.lifecycle.LiveData
import com.dicoding.parsingjson.data.database.FavoriteUser
import com.dicoding.parsingjson.data.database.UserDao
import com.dicoding.parsingjson.data.model.GithubResponse
import com.dicoding.parsingjson.data.model.ItemsItem
import com.dicoding.parsingjson.data.network.ApiService
import retrofit2.http.Query

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
) {

    suspend fun getListUsers(query: String): GithubResponse {
        return apiService.getListUsers(query)
    }

    suspend fun addFavorite(item: FavoriteUser) {
        userDao.insert(item)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return userDao.getFavoriteUserByUsername(username)
    }

    suspend fun deleteFavorite(item: FavoriteUser) {
        userDao.delete(item)
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>{
        return userDao.getFavoriteUser()
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