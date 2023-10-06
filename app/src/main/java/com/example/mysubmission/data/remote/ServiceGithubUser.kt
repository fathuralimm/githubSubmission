package com.example.mysubmission.data.remote


import com.example.mysubmission.BuildConfig
import com.example.mysubmission.data.model.ItemsItem
import com.example.mysubmission.data.model.UserDetailResponse
import com.example.mysubmission.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ServiceGithubUser {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUser(
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ItemsItem>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): UserDetailResponse

    @JvmSuppressWildcards
    @GET("/users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ItemsItem>

    @JvmSuppressWildcards
    @GET("/users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ItemsItem>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun getSearchUser(
        @QueryMap params: Map<String, Any>,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): UserResponse
}