package com.dak.watchdog.payload

data class SubscribeService (
    val services: List<String>,
    val watch: List<String>
)
