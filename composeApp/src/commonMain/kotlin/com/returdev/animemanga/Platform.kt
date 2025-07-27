package com.returdev.animemanga

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform