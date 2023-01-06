package com.dicoding.parsingjson

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.parsingjson.model.GithubResponse
import com.dicoding.parsingjson.model.ItemsItem
import com.dicoding.parsingjson.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MainViewModel : ViewModel() {

    private val _listReview = MutableLiveData<List<ItemsItem>>()
    val listReview: LiveData<List<ItemsItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    init {
        searchUser("arif")
    }

    fun searchUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                if (response.isSuccessful) {
                    _listReview.value = response.body()?.items as List<ItemsItem>
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}