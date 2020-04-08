package com.anil.newapp.di

import androidx.room.Room
import com.anil.newapp.R
import com.anil.newapp.datasource.OfflineDataSource
import com.anil.newapp.persistance.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(), AppDatabase::class.java,
            androidApplication().getString(R.string.database)
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().articleDao() }

    single { OfflineDataSource(get()) }

}
