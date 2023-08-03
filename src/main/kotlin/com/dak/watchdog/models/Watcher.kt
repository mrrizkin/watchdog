package com.dak.watchdog.models

import jakarta.persistence.*

@Entity
@Table(name = "watcher")
data class Watcher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    var name: String,
    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,
    @ManyToMany
    @JoinTable(
        name = "service_watcher",
        joinColumns = [JoinColumn(name = "watcher_id")],
        inverseJoinColumns = [JoinColumn(name = "service_id")]
    )
    var services: List<Service> = listOf()
)