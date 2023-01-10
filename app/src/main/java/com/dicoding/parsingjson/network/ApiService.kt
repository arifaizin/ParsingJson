package com.dicoding.parsingjson.network

import com.dicoding.parsingjson.model.DetailUserResponse
import com.dicoding.parsingjson.model.GithubResponse
import com.dicoding.parsingjson.model.ResponseUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getListUsers(@Query("q") query: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>
}