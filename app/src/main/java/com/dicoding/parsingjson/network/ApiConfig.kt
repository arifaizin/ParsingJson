package com.dicoding.parsingjson.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

        companion object{
            fun getApiService(): ApiService {
                val loggingInterceptor =
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                val authInterceptor = Interceptor { chain ->
                    val req = chain.request()
                    val requestHeaders = req.newBuilder()
                        .addHeader("Authorization", "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")
                        .build()
                    chain.proceed(requestHeaders)
                }
                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(authInterceptor)
                    .build()
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                return retrofit.create(ApiService::class.java)
            }
        }
    }