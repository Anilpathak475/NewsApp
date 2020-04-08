package com.anil.newapp.di

import com.anil.newapp.repository.ArticleRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { ArticleRepositoryImpl(get(), get()) }

}
