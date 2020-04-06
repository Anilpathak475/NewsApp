package com.anil.newapp.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anil.newapp.persistance.entitiy.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}