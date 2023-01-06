package com.dicoding.parsingjson.network

import com.dicoding.parsingjson.model.GithubResponse
import com.dicoding.parsingjson.model.ResponseUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //get list users with query
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getListUsers(@Query("q") query: String): Call<GithubResponse>

    //get list user by id using path
    @GET("api/users/{id}")
    fun getUser(@Path("id") id: String): Call<ResponseUser>

    //post user using field x-www-form-urlencoded
    @FormUrlEncoded
    @POST("api/users")
    fun createUser(
        @Field("name") name: String,
        @Field("job") job: String
    ): Call<ResponseUser>

    //upload file using multipart
    @Multipart
    @PUT("api/uploadfile")
    fun updateUser(
        @Part("file") file: MultipartBody.Part,
        @PartMap data: Map<String, RequestBody>
    ): Call<ResponseUser>
}