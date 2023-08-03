package com.dak.watchdog.controllers

import com.dak.watchdog.models.Notifier
import com.dak.watchdog.payload.Response
import com.dak.watchdog.services.NotifierService
import com.dak.watchdog.services.ProviderService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/notifier")
class NotifierController(
    val notifierService: NotifierService,
    val providerService: ProviderService
) {
    @GetMapping
    fun list(): Response {
        return try {
            val notifiers = notifierService.get()

            Response(
                success = true,
                data = notifiers,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to get list of notifiers"
            )
        }
    }

    @PostMapping("/{provider_id}")
    fun create(@PathVariable providerId: Long, @RequestBody payload: Notifier): Response {
        return try {
            val provider = providerService.find(providerId)

            if (!provider.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Provider not found"
                )
            }

            payload.provider = provider.get()
            val service = notifierService.save(payload)

            Response(
                success = true,
                data = service,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to create notifier"
            )
        }
    }

    @PutMapping("/{id}")
    fun edit(@PathVariable id: Long, @RequestBody payload: Notifier): Response {
        return try {
            val notifier = notifierService.find(id)

            if (!notifier.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Notifier not found"
                )
            }

            notifier.get().name = payload.name
            notifier.get().phoneNumber = payload.phoneNumber

            val result = notifierService.save(notifier.get())

            Response(
                success = true,
                data = result,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to edit notifier"
            )
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Response {
        return try {
            val notifier = notifierService.find(id)

            if (!notifier.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Notifier not found"
                )
            }

            val result = notifierService.delete(notifier.get())

            Response(
                success = true,
                data = result,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to delete notifier"
            )
        }
    }
}
