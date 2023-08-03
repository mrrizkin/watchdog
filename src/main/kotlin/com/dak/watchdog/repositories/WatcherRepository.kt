package com.dak.watchdog.repositories

import com.dak.watchdog.models.Watcher
import org.springframework.data.jpa.repository.JpaRepository

interface WatcherRepository: JpaRepository<Watcher, Long>
