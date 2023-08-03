package com.dak.watchdog.models

import jakarta.persistence.*

@Entity
@Table(name = "provider")
data class Provider(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    var name: String,
    @Column(name = "base_uri", nullable = false)
    var baseURI: String,
    var auth: String,
    var token: String,
    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    val notifiers: List<Notifier> = listOf()
)