package com.anil.newapp.repository

import com.anil.newapp.model.ArticleResponse
import com.anil.newapp.networking.NewsClient
import com.anil.newapp.networking.Resource
import com.anil.newapp.networking.ResponseHandler

class NewsRepository constructor(
    private val newsClient: NewsClient,
    private val responseHandler: ResponseHandler
) {
    suspend fun loadTopHeadlines(): Resource<ArticleResponse> {
        return try {
            val response = newsClient.fetchNews()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}
