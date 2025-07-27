package com.returdev.animemanga

import android.app.Application
import com.returdev.animemanga.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class AnimeMangaVaultKMPApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@AnimeMangaVaultKMPApplication)
        }
    }

}