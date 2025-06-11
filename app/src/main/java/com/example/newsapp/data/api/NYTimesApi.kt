package com.example.newsapp.data.api

import com.example.newsapp.data.model.ArticleResponse
import com.example.newsapp.data.model.TopStoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTimesApi {
    @GET("topstories/v2/home.json")
    suspend fun getTopStories(@Query("api-key") apiKey: String): TopStoriesResponse

    @GET("search/v2/articlesearch.json")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("api-key") apiKey: String
    ): ArticleResponse
}