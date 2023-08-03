package com.dak.watchdog.repositories

import com.dak.watchdog.models.Service
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceRepository: JpaRepository<Service, Long>
