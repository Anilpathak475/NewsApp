package com.anil.newapp.di

import com.anil.newapp.repository.NewsBoundaryCallback
import org.koin.dsl.module

val paginationModule = module {
    single { NewsBoundaryCallback(get(), get()) }

}