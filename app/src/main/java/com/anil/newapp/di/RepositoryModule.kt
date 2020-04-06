package com.anil.newapp.di

import com.anil.newapp.repository.NewsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { NewsRepository(get(), get()) }

}
