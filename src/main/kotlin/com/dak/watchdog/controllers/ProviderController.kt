package com.dak.watchdog.controllers

import com.dak.watchdog.models.Notifier
import com.dak.watchdog.models.Provider
import com.dak.watchdog.payload.Response
import com.dak.watchdog.services.NotifierService
import com.dak.watchdog.services.ProviderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/provider")
class ProviderController(
    val providerService: ProviderService,
    val notifierService: NotifierService
) {
    @GetMapping
    fun list(): Response {
        return try {
            val providers = providerService.get()

            Response(
                success = true,
                data = providers,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to get list of providers"
            )
        }
    }

    @PostMapping
    fun create(@RequestBody payload: Provider): Response {
        return try {
            val service = providerService.save(payload)

            Response(
                success = true,
                data = service,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to create provider"
            )
        }
    }

    @PutMapping("/{id}")
    fun edit(@PathVariable id: Long, @RequestBody payload: Provider): Response {
        return try {
            val provider = providerService.find(id)

            if (!provider.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Provider not found"
                )
            }

            provider.get().token = payload.token
            provider.get().name = payload.name
            provider.get().baseURI = payload.baseURI
            provider.get().auth = payload.auth

            val service = providerService.save(provider.get())

            Response(
                success = true,
                data = service,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to edit provider"
            )
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Response {
        return try {
            val provider = providerService.find(id)

            if (!provider.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Provider not found"
                )
            }

            val result = providerService.delete(provider.get())

            Response(
                success = true,
                data = result,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to edit provider"
            )
        }
    }

    @GetMapping("/{id}/notifiers")
    fun listNotifiers(@PathVariable id: Long): Response {
        return try {
            val provider = providerService.find(id)

            if (!provider.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Provider not found"
                )
            }

            Response(
                success = true,
                data = provider.get().notifiers,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to get list of providers"
            )
        }
    }

    @PostMapping("/{id}/notifiers")
    fun createNotifier(@PathVariable id: Long, @RequestBody payload: Notifier): Response {
        return try {
            val provider = providerService.find(id)

            if (!provider.isPresent) {
                return Response(
                    success = false,
                    data = null,
                    message = "Provider not found"
                )
            }

            payload.provider = provider.get()

            val notifier = notifierService.save(payload)

            Response(
                success = true,
                data = notifier,
                message = "Success"
            )
        } catch (e: Exception) {
            Response(
                success = false,
                data = null,
                message = e.message ?: "Failed to get list of providers"
            )
        }
    }
}
