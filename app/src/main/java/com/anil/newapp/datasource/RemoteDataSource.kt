package com.anil.newapp.datasource

import com.anil.newapp.model.ArticleResponse
import com.anil.newapp.networking.NewsApi
import io.reactivex.Single

class RemoteDataSource(
    private val newsApi: NewsApi
) {
    fun getArticles(
        page: Int
    ): Single<ArticleResponse> =
        newsApi.fetchNews(page = page)

    fun getArticlesByTopic(
        topic: String
    ): Single<ArticleResponse> =
        newsApi.fetchNews()


}