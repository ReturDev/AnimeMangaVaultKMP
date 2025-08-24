package com.returdev.animemanga.core

/**
 * A multiplatform logging utility.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object Logger {

    /**
     * Logs a debug-level message.
     *
     * @param tag A tag to identify the source of the log message.
     * @param message The message to log.
     */
    fun d(tag: String, message: String)

    /**
     * Logs an info-level message.
     *
     * @param tag A tag to identify the source of the log message.
     * @param message The message to log.
     */
    fun i(tag: String, message: String)

    /**
     * Logs a warning-level message.
     *
     * @param tag A tag to identify the source of the log message.
     * @param message The message to log.
     * @param throwable An optional [Throwable] associated with the warning.
     */
    fun w(tag: String, message: String, throwable: Throwable? = null)

    /**
     * Logs an error-level message.
     *
     * @param tag A tag to identify the source of the log message.
     * @param message The message to log.
     * @param throwable An optional [Throwable] associated with the error.
     */
    fun e(tag: String, message: String, throwable: Throwable? = null)
}
