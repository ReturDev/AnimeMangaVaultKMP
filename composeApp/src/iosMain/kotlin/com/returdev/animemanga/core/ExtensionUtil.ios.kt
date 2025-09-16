package com.returdev.animemanga.core

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun String.format(vararg args : Any?) : String {
    return NSString.stringWithFormat(this, *args)
}