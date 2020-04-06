package com.anil.newapp.networking

import com.anil.newapp.model.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsClient {
    @GET("v2/top-headlines")
    suspend fun fetchNews(
        @Query("country") country: String = "DE",
        @Query("apiKey") apiKey: String = "8e748d8db9c6466791912adc282e3bbc"
    ): ArticleResponse
}
