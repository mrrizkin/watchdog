package com.dak.watchdog.payload

data class TelegramMessage(
    val phone: String,
    val to: String,
    val message: String
)
