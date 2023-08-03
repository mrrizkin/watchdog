package com.dak.watchdog.payload

data class Response(
    val success: Boolean,
    val data: Any?,
    val message: String
)
