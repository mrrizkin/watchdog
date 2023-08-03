package com.dak.watchdog.controllers

import com.dak.watchdog.models.Watcher
import com.dak.watchdog.payload.Response
import com.dak.watchdog.payload.SubscribeService
import com.dak.watchdog.services.ServiceService
import com.dak.watchdog.services.WatcherService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/watcher")
class WatcherController(
    val watcherService: WatcherService,
    val serviceService: ServiceService
) {
    @GetMapping
    fun list(): Response {
        return try {
            val watchers = watcherService.get()

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

    @PostMapping
    fun create(@RequestBody payload: Watcher): Response {
        return try {
            val watcher = watcherService.save(payload)

            Response(
                success = true,
                data = watcher,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to create watcher"
            )
        }
    }

    @PutMapping("/{id}")
    fun edit(@PathVariable id: Long, @RequestBody payload: Watcher): Response {
        return try {
            val watcher = watcherService.find(id)

            if (!watcher.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Service not found"
                )
            }

            watcher.get().name = payload.name
            watcher.get().phoneNumber = payload.phoneNumber

            watcherService.save(watcher.get())

            Response(
                success = true,
                data = watcher,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to edit watcher"
            )
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Response {
        return try {
            val watcher = watcherService.find(id)

            if (!watcher.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Service not found"
                )
            }

            // get all services that are this watcher subscribes to
            val serviceIds = watcher.get().services.map { it.id }
            val services = serviceService.gets(serviceIds)

            for (service in services) {
                service.watchers = service.watchers.filter { it.id != watcher.get().id }.toMutableList()
                serviceService.save(service)
            }

            val result = watcherService.delete(watcher.get())

            Response(
                success = true,
                data = result,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to delete watcher"
            )
        }
    }


    @PostMapping("/{id}/services")
    fun subscribe(@PathVariable id: Long, @RequestBody payload: SubscribeService): Response {
        return try {
            val watcher = watcherService.find(id)

            if (!watcher.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Watcher not found"
                )
            }

            val serviceIds = mutableListOf<Long>()

            for ((index, watch) in payload.watch.withIndex()) {
                if (watch == "on") {
                    serviceIds.add(payload.services[index].toLong())
                }
            }

            val services = serviceService.gets(serviceIds)

            watcher.get().services = services

            val result = watcherService.save(watcher.get())

            Response(
                success = true,
                data = result,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to subscribe services"
            )
        }
    }
}
