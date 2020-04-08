package com.anil.newapp.datasource

import com.anil.newapp.persistance.entitiy.Article

interface DataSource {
    fun getArticles(response: (articles: List<Article>) -> Unit, error: (error: String) -> Unit)
}