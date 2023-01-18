package com.dicoding.parsingjson.data.network

import com.dicoding.parsingjson.data.model.DetailUserResponse
import com.dicoding.parsingjson.data.model.GithubResponse
import com.dicoding.parsingjson.data.model.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getListUsers(@Query("q") query: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}