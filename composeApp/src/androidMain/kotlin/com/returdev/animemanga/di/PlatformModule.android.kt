package com.returdev.animemanga.di

import com.returdev.animemanga.data.cache.getDatabase
import com.returdev.animemanga.data.library.getLibraryDataBase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun platformModule() : Module = module {
    singleOf(::getDatabase)
    singleOf(::getLibraryDataBase)
}