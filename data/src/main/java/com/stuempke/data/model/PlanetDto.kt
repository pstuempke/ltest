package com.stuempke.data.model

import com.stuempke.core.domain.model.Planet
import kotlinx.serialization.Serializable
import okhttp3.internal.toLongOrDefault

@Serializable
data class PlanetDto(
    val name: String,
    val climate: String,
    val gravity: String,
    val population: String,
    val diameter: String,
    val terrain: String,
    val url: String,
)

fun PlanetDto.toDomain() = Planet(
    name = name,
    climate = climate,
    gravity = gravity,
    population = population.toLongOrDefault(0L),
    diameter = diameter.toDoubleOrNull() ?: 0.0,
    terrain = terrain,
    url = url,
)

