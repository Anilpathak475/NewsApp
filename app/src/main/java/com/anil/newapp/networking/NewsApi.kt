package com.anil.newapp.networking

import com.anil.newapp.model.ArticleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/everything")
    fun fetchNews(
        @Query("q") country: String = "Corona",
        @Query("from") from: String = "2020-04-08",
        @Query("sortBy") sortBy: String = "latest",
        @Query("apiKey") apiKey: String = "8e748d8db9c6466791912adc282e3bbc",
        @Query("page") page: Int = 1

    ): Single<ArticleResponse>
}



