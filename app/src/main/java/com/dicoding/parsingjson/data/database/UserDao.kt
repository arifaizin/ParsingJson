package com.dicoding.parsingjson.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: FavoriteUser)
    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>
    @Delete
    suspend fun delete(item: FavoriteUser)
    @Query("SELECT * from FavoriteUser")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>
}