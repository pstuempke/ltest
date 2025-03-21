package com.stuempke.luzuatest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Planet(
    val name: String,
    val climate: String,
    val gravity: String,
    val population: String,
    val diameter: String,
    val terrain: String,
    val url: String,
)
