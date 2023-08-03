package com.dak.watchdog.repositories

import com.dak.watchdog.models.Notifier
import org.springframework.data.jpa.repository.JpaRepository

interface NotifierRepository: JpaRepository<Notifier, Long>
