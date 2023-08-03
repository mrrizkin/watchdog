package com.dak.watchdog.models

import com.fasterxml.jackson.annotation.*
import jakarta.persistence.*

@Entity
@Table(name = "service")
data class Service(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var url: String,
    @Column(nullable = false)
    var isDown: Boolean = false,
    @Column(nullable = false)
    val isWatching: Boolean = true,
    @ManyToMany(mappedBy = "services", fetch = FetchType.EAGER, cascade = [CascadeType.DETACH])
    @JsonIgnore
    @JsonManagedReference
    @JsonBackReference
    var watchers: List<Watcher> = listOf(),
)
