package com.dicoding.parsingjson.network

import com.dicoding.parsingjson.model.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/users")
    fun getListUsers(@Query("page") page: String): Call<ResponseUser>
}