package com.anil.newapp.di

import com.anil.newapp.datasource.LocalDataSource
import com.anil.newapp.datasource.RemoteDataSource
import com.anil.newapp.repository.ArticleRepository
import com.anil.newapp.repository.ArticleRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { ArticleRepositoryImpl(get(), get()) }
    single { provideArticleRepository(get(), get()) }
}


fun provideArticleRepository(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
): ArticleRepository = ArticleRepositoryImpl(remoteDataSource, localDataSource)