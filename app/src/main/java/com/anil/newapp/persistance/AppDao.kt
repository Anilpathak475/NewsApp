package com.anil.newapp.persistance

import androidx.room.Dao
import androidx.room.Query
import com.anil.newapp.base.BaseDao
import com.anil.newapp.persistance.entitiy.Article

@Dao
interface ArticleDao : BaseDao<Article> {

    @Query("SELECT * FROM news")
    fun getCachedArticles(): List<Article>

    @Query("DELETE FROM news")
    fun deleteAllArticles()
}

