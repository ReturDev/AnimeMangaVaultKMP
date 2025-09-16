package com.returdev.animemanga.core

actual fun String.format(vararg args : Any?) : String {
    return String.format(this, *args)
}