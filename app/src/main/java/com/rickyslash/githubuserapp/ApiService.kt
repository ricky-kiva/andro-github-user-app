package com.rickyslash.githubuserapp

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/search/users")
    fun getUsers(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("/users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<DetailFollowsResponseItem>>

    @GET("/users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<DetailFollowsResponseItem>>
}