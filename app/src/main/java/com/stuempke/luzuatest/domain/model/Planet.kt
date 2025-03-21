package com.stuempke.luzuatest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Planet(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String
)
