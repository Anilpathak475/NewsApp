package com.anil.newapp.model

import com.anil.newapp.persistance.entitiy.Article

data class ArticleResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)