package com.dak.watchdog.services

import com.dak.watchdog.models.Provider
import com.dak.watchdog.models.Service
import com.dak.watchdog.payload.TelegramMessage
import com.dak.watchdog.payload.WhatsappMessage
import com.dak.watchdog.repositories.ProviderRepository
import org.springframework.data.domain.Sort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import java.util.Optional

@org.springframework.stereotype.Service
class ProviderService(
    val providerRepository: ProviderRepository
) {
    fun get(): MutableList<Provider> {
        return providerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
    }

    fun find(id: Long): Optional<Provider> {
        return providerRepository.findById(id)
    }

    fun save(provider: Provider): Provider {
        return providerRepository.save(provider)
    }

    fun delete(provider: Provider) {
        return providerRepository.delete(provider)
    }

    fun sendNotify(providers: MutableList<Provider>, service: Service) {
        val msg = if (service.isDown) "Server ${service.name} is down" else "Server ${service.name} is up"

        for (provider in providers) {
            if (provider.name == "telegram") {
                telegramClient(service, provider, msg)
            }

            if (provider.name == "whatsapp") {
                whatsappClient(service, provider, msg)
            }
        }
    }

    private fun telegramClient(service: Service, provider: Provider, msg: String) {
        val headers = HttpHeaders()
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        headers.set("Authorization", "${provider.auth} ${provider.token}")

        for (notifier in provider.notifiers) {
            for (watcher in service.watchers) {
                var receiver = watcher.phoneNumber

                if (receiver[0] == '0') {
                    receiver = "+62${receiver.substring(1)}"
                }

                if (receiver[0] == '8') {
                    receiver = "+62$receiver"
                }

                if (receiver[0] == '6') {
                    receiver = "+$receiver"
                }

                // skip if receiver is not a phone number
                if (receiver[0] != '+') {
                    continue
                }

                val restTemplate = RestTemplate()

                val payload = TelegramMessage(
                    phone = notifier.phoneNumber,
                    to = receiver,
                    message = msg
                )
                val requestEntity = HttpEntity(payload, headers)
                restTemplate.postForEntity(provider.baseURI, requestEntity, String::class.java)
            }
        }
    }

    private fun whatsappClient(service: Service, provider: Provider, msg: String) {
        val headers = HttpHeaders()
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        headers.set("access-token", provider.token)

        for (notifier in provider.notifiers) {
            for (watcher in service.watchers) {
                var receiver = watcher.phoneNumber

                if (receiver[0] == '0') {
                    receiver = "62${receiver.substring(1)}"
                }

                if (receiver[0] == '8') {
                    receiver = "62$receiver"
                }

                if (receiver[0] == '+') {
                    receiver = receiver.substring(1)
                }

                val restTemplate = RestTemplate()
                val payload = WhatsappMessage(
                    deviceNumber = notifier.phoneNumber,
                    to = receiver,
                    message = msg
                )
                val requestEntity = HttpEntity(payload, headers)

                restTemplate.postForEntity(provider.baseURI, requestEntity, String::class.java)
            }
        }
    }
}
