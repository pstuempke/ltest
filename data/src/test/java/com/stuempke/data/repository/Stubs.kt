package com.stuempke.data.repository

import com.stuempke.core.domain.model.Planet

val planetStub = Planet(
    name = "name",
    climate = "climate",
    gravity = "gravity",
    population = 0,
    diameter = 0.0,
    terrain = "terrain",
    url = "url"
)

val planetDtoStub = com.stuempke.data.model.PlanetDto(
    name = "name",
    climate = "climate",
    gravity = "gravity",
    population = "0",
    diameter = "0.0",
    terrain = "terrain",
    url = "url"
)