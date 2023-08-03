package com.dak.watchdog

import com.dak.watchdog.services.ProviderService
import com.dak.watchdog.services.ServiceService
import org.hibernate.Hibernate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class Routines(
    val serviceService: ServiceService,
    val providerService: ProviderService
) {
    @Scheduled(cron = "0 * * * * *")
    fun run() {
        val services = serviceService.get()
        val providers = providerService.get()

        for (service in services) {
            val restTemplate = RestTemplate()

            try {
                val result = restTemplate.getForEntity(service.url, String::class.java)

                if (result.statusCode.value() != 200 && !service.isDown) {
                    service.isDown = true
                    providerService.sendNotify(providers, service)
                    serviceService.save(service)
                } else if (service.isDown) {
                    service.isDown = false
                    providerService.sendNotify(providers, service)
                    serviceService.save(service)
                }
            } catch (e: Exception) {
                if (!service.isDown) {
                    service.isDown = true
                    providerService.sendNotify(providers, service)
                    serviceService.save(service)
                }
            }
        }
    }
}
