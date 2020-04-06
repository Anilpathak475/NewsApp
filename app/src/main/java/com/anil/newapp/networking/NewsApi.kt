package com.anil.newapp.networking

import com.anil.newapp.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    fun fetchNews(
        @Query("country") country: String = "US",
        @Query("apiKey") apiKey: String = "8e748d8db9c6466791912adc282e3bbc"
    ): Call<ArticleResponse>
}



