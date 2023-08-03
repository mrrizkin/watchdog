package com.dak.watchdog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WatchdogApplication

fun main(args: Array<String>) {
	runApplication<WatchdogApplication>(*args)
}
