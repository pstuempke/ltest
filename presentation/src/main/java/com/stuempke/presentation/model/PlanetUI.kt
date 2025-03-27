package com.stuempke.presentation.model

import com.stuempke.core.domain.model.Planet

data class PlanetUI(
    val name: String = "",
    val climate: String = "",
    val gravity: String = "",
    val population: Long = 0,
    val diameter: Double = 0.0,
    val terrain: String = "",
    val url: String = ""
)

fun Planet.toPresentation() = PlanetUI(
    name = name,
    climate = climate,
    gravity = gravity,
    population = population,
    diameter = diameter,
    terrain = terrain,
    url = url,
)