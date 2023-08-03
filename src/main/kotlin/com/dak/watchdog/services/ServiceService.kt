package com.dak.watchdog.services

import com.dak.watchdog.models.Service
import com.dak.watchdog.repositories.ServiceRepository
import org.springframework.data.domain.Sort
import java.util.*

@org.springframework.stereotype.Service
class ServiceService(val serviceRepository: ServiceRepository) {
    fun get(): MutableList<Service> {
        return serviceRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
    }

    fun find(id: Long): Optional<Service> {
        return serviceRepository.findById(id)
    }

    fun delete(service: Service) {
        return serviceRepository.delete(service)
    }

    fun save(service: Service): Service {
        return serviceRepository.save(service)
    }

    fun gets(serviceIds: List<Long>): MutableList<Service> {
        return serviceRepository.findAllById(serviceIds)
    }
}