package com.dicoding.parsingjson.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

    class ApiConfig {
        companion object{
            fun getApiService(): ApiService {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://reqres.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                return retrofit.create(ApiService::class.java)
            }
        }
    }