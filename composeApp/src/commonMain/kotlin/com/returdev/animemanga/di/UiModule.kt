package com.returdev.animemanga.di

import com.returdev.animemanga.ui.screen.detail.anime.AnimeDetailViewModel
import com.returdev.animemanga.ui.screen.home.HomeViewModel
import com.returdev.animemanga.ui.screen.library.LibraryViewModel
import com.returdev.animemanga.ui.screen.search.anime.AnimeSearchViewModel
import com.returdev.animemanga.ui.screen.search.manga.MangaSearchViewModel
import com.returdev.animemanga.ui.screen.showmore.ShowMoreViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val uiModule = module {
    factoryOf(::HomeViewModel)
    factoryOf(::ShowMoreViewModel)
    factoryOf(::LibraryViewModel)
    factoryOf(::AnimeSearchViewModel)
    factoryOf(::MangaSearchViewModel)
    factoryOf(::AnimeDetailViewModel)
}