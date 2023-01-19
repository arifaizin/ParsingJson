package com.dicoding.parsingjson.data

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import com.dicoding.parsingjson.data.database.FavoriteUser
import com.dicoding.parsingjson.data.database.UserDao
import com.dicoding.parsingjson.data.model.GithubResponse
import com.dicoding.parsingjson.data.model.ItemsItem
import com.dicoding.parsingjson.data.network.ApiService
import com.dicoding.parsingjson.ui.setting.SettingPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.http.Query

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val settingPreferences: SettingPreferences
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

    fun getThemeSetting(): Flow<Boolean> {
        return settingPreferences.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingPreferences.saveThemeSetting(isDarkModeActive)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: UserDao,
            settingPreferences: SettingPreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, newsDao, settingPreferences)
            }.also { instance = it }
    }
}