package com.anil.newapp.networking

import com.anil.newapp.model.ArticleResponse

class ArticleClient(private val newsApi: NewsApi) {

    fun fetchArticles(
        onResult: (response: ApiResponse<ArticleResponse>) -> Unit
    ) = this.newsApi.fetchNews().transform(onResult)

}
