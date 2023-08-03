package com.dak.watchdog.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "notifier")
data class Notifier(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var deleted: Boolean = false,
    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,
    @ManyToOne
    @JoinColumn(name = "provider_id")
    @JsonIgnore
    var provider: Provider = Provider(0, "", "", "", "", listOf())
)