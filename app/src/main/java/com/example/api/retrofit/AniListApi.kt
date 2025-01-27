package com.example.api.retrofit

import com.example.api.model.GraphQLRequest
import com.example.api.model.UserAnimeListResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface AniListApi {

    @POST("graphql")
    suspend fun getUserAnimeList(@Body request: GraphQLRequest): UserAnimeListResponse


}