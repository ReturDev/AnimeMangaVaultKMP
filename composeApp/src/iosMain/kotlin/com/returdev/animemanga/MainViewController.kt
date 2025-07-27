package com.returdev.animemanga

import androidx.compose.ui.window.ComposeUIViewController
import com.returdev.animemanga.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = initKoin() ) { App() }