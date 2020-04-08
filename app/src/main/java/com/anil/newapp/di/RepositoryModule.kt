package com.anil.newapp.di

import com.anil.newapp.datasource.LocalDataSource
import com.anil.newapp.datasource.OnlineDataSource
import com.anil.newapp.repository.ArticleRepository
import com.anil.newapp.repository.ArticleRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { ArticleRepositoryImpl(get(), get()) }
    single { provideArticleRepository(get(), get()) }
}


fun provideArticleRepository(
    onlineDataSource: OnlineDataSource,
    localDataSource: LocalDataSource
): ArticleRepository = ArticleRepositoryImpl(onlineDataSource, localDataSource)