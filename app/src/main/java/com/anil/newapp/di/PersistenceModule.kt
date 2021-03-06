package com.anil.newapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.anil.newapp.R
import com.anil.newapp.datasource.LocalDataSource
import com.anil.newapp.persistance.AppDatabase
import com.anil.newapp.persistance.InternalErrorConverter
import com.anil.newapp.persistance.InternalErrorConverterImpl
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

    single { LocalDataSource(get(), get()) }
    single {
        provideSettingsPreferences(androidApplication())
    }

    single<SharedPreferences.Editor> {
        provideSettingsPreferences(androidApplication()).edit()
    }
    single {
        provideInternalErrorConverter()
    }

}


private fun provideSettingsPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(PREFERENCES_LOCAL_SYNC, Context.MODE_PRIVATE)

private fun provideInternalErrorConverter(): InternalErrorConverter =
    InternalErrorConverterImpl()

const val PREFERENCES_LOCAL_SYNC = "localSyncManager"