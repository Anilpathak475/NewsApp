package com.anil.newapp.datasource

import com.anil.newapp.networking.ApiResponse
import com.anil.newapp.networking.ArticleClient
import com.anil.newapp.networking.message
import com.anil.newapp.persistance.entitiy.Article

class OnlineDataSource(private val articleClient: ArticleClient) : DataSource {
    override fun getArticles(
        response: (articles: List<Article>) -> Unit,
        error: (error: String) -> Unit
    ) {
        articleClient.fetchArticles { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> apiResponse.data?.let {
                    response(it.articles)
                }
                is ApiResponse.Failure.Error -> error(apiResponse.message())
                is ApiResponse.Failure.Exception -> error(apiResponse.message())
            }
        }
    }

}