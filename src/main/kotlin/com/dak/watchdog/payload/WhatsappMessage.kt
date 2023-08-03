package com.dak.watchdog.payload

import com.fasterxml.jackson.annotation.JsonProperty

data class WhatsappMessage(
    @JsonProperty("device_number")
    val deviceNumber: String,
    val to: String,
    val message: String
)
