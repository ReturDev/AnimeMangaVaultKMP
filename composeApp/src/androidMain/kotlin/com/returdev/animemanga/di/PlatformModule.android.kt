package com.returdev.animemanga.di

import com.returdev.animemanga.data.cache.getDatabase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun platformModule() : Module = module {
    singleOf(::getDatabase)
}