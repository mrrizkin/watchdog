package com.dak.watchdog.repositories

import com.dak.watchdog.models.Provider
import org.springframework.data.jpa.repository.JpaRepository

interface ProviderRepository: JpaRepository<Provider, Long>
