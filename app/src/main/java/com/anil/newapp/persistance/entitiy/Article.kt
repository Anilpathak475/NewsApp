package com.anil.newapp.persistance.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val title: String? = "",
    val url: String? = "",
    val urlToImage: String? = ""
)