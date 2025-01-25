package com.example.api.retrofit

import com.example.api.model.UserAnimeListResponse
import com.example.api.model.UserIdResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface AniListApi {
    @POST("graphql")
    fun getUserId(@Query("variables") variables: String): Call<UserIdResponse>

    @POST("graphql")
    fun getUserAnimeList(@Query("variables") variables: String, userId: Int): Call<UserAnimeListResponse>
}