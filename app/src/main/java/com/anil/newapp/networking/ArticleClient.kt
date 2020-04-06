package com.anil.newapp.networking

import com.anil.newapp.model.ArticleResponse
import com.skydoves.disneymotions.network.ApiResponse

class ArticleClient(private val newsApi: NewsApi) {

    fun fetchArticles(
        onResult: (response: ApiResponse<ArticleResponse>) -> Unit
    ) = this.newsApi.fetchNews().transform(onResult)

}
