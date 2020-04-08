package com.anil.newapp.persistance

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.anil.newapp.base.BaseDao
import com.anil.newapp.persistance.entitiy.Article
import io.reactivex.Single

@Dao
interface ArticleDao : BaseDao<Article> {

    @Query("SELECT * FROM news")
    fun getCachedArticles(): Single<List<Article>>

    @Query("DELETE FROM news")
    fun deleteAllArticles()


    @Query("SELECT * FROM news ORDER BY id DESC")
    fun getAllArticlesAsDataSourceOffline(): DataSource.Factory<Int, Article>
}

