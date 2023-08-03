package com.dak.watchdog.services

import com.dak.watchdog.models.Notifier
import com.dak.watchdog.repositories.NotifierRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class NotifierService(
    val notifierRepository: NotifierRepository
) {
    fun get(): MutableList<Notifier> {
        return notifierRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
    }

    fun find(id: Long): Optional<Notifier> {
        return notifierRepository.findById(id)
    }

    fun delete(notifier: Notifier) {
        return notifierRepository.delete(notifier)
    }

    fun save(notifier: Notifier): Notifier {
        return notifierRepository.save(notifier)
    }
}