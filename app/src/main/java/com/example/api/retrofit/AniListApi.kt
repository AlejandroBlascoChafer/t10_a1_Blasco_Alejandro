package com.example.api.retrofit

import com.example.api.GetUserAnimeListQuery
import com.example.api.model.GraphQLRequest
import com.example.api.model.UserAnimeListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AniListApi {

    @POST("graphql")
    suspend fun getUserAnimeList(@Body request: GraphQLRequest): UserAnimeListResponse


}