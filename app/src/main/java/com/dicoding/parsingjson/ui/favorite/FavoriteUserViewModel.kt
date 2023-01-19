package com.dicoding.parsingjson.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.parsingjson.data.UserRepository
import com.dicoding.parsingjson.data.database.FavoriteUser
import com.dicoding.parsingjson.data.model.DetailUserResponse
import com.dicoding.parsingjson.data.model.ItemsItem
import com.dicoding.parsingjson.data.network.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> {
        return userRepository.getFavoriteUser()
    }
}