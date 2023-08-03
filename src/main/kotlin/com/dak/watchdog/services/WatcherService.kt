package com.dak.watchdog.services

import com.dak.watchdog.models.Watcher
import com.dak.watchdog.repositories.WatcherRepository
import org.springframework.data.domain.Sort
import java.util.Optional

@org.springframework.stereotype.Service
class WatcherService(
    val watcherRepository: WatcherRepository
) {
    fun get(): MutableList<Watcher> {
        return watcherRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
    }

    fun gets(watcherIds: List<Long>): MutableList<Watcher> {
        return watcherRepository.findAllById(watcherIds)
    }

    fun find(id: Long): Optional<Watcher> {
        return watcherRepository.findById(id)
    }

    fun save(watcher: Watcher): Watcher {
        return watcherRepository.save(watcher)
    }

    fun delete(watcher: Watcher) {
        return watcherRepository.delete(watcher)
    }
}
