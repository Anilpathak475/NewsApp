package com.anil.newapp.di

import com.anil.newapp.repository.ArticleRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { ArticleRepository(get(), get()) }

}
