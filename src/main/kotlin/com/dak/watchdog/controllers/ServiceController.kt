package com.dak.watchdog.controllers

import com.dak.watchdog.models.Service
import com.dak.watchdog.payload.Response
import com.dak.watchdog.services.ServiceService
import com.dak.watchdog.services.WatcherService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/services")
class ServiceController(
    val serviceService: ServiceService,
    val watcherService: WatcherService
) {
    @GetMapping
    fun list(): Response {
        return try {
            val services = serviceService.get()

            Response(
                success = true,
                data = services,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to get list of services"
            )
        }
    }

    @PostMapping
    fun create(@RequestBody payload: Service): Response {
        return try {
            val service = serviceService.save(payload)

            Response(
                success = true,
                data = service,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to create service"
            )
        }
    }

    @PutMapping("/{id}")
    fun edit(@PathVariable id: Long, @RequestBody payload: Service): Response {
        return try {
            val service = serviceService.find(id)

            if (!service.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Service not found"
                )
            }

            service.get().name = payload.name
            service.get().url = payload.url

            serviceService.save(service.get())

            Response(
                success = true,
                data = service,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to edit service"
            )
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Response {
        return try {
            val service = serviceService.find(id)

            if (!service.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Service not found"
                )
            }

            // delete service from watcher that has this service
            val watcherIds = service.get().watchers.map { it.id }
            val watchers = watcherService.gets(watcherIds)

            watchers.forEach { watcher ->
                watcher.services = watcher.services.filter { it.id != service.get().id }
                watcherService.save(watcher)
            }

            val result = serviceService.delete(service.get())

            Response(
                success = true,
                data = result,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to delete service"
            )
        }
    }

    @GetMapping("/{id}/watchers")
    fun listWatchers(@PathVariable id: Long): Response {
        return try {
            val service = serviceService.find(id)

            if (!service.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Service not found"
                )
            }

            val watchers = service.get().watchers

            Response(
                success = true,
                data = watchers,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to get list of watchers"
            )
        }
    }
}
