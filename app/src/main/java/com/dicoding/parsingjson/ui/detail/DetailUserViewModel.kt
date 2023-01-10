package com.dicoding.parsingjson.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.parsingjson.model.DetailUserResponse
import com.dicoding.parsingjson.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailByUsername(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                if (response.isSuccessful) {
                    _detailUser.value = response.body() as DetailUserResponse
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}