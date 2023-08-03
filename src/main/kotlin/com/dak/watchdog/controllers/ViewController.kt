package com.dak.watchdog.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController {

    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/service")
    fun services(): String {
        return "service"
    }

    @GetMapping("/provider")
    fun providers(): String {
        return "provider"
    }

    @GetMapping("/watcher")
    fun watchers(): String {
        return "watcher"
    }
}