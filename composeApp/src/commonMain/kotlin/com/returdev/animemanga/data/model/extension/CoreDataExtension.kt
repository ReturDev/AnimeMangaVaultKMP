package com.returdev.animemanga.data.model.extension

/**
 * Converts any object to a lowercase [String], if it is not null.
 *
 * This is a safe extension that first calls [toString] on the object,
 * then converts the result to lowercase. Returns `null` if the receiver is null.
 *
 * @receiver The object to convert to lowercase.
 * @return The lowercase string representation of the object, or `null` if the receiver is null.
 */
fun Any?.toLowerCase(): String? {
    return this?.toString()?.lowercase()
}
