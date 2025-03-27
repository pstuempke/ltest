package com.stuempke.presentation.model

import org.junit.Assert.*
import org.junit.Test

class PlanetUIKtTest {

    @Test
    fun testToPresentation() {
        val planet = com.stuempke.core.domain.model.Planet(
            name = "name",
            climate = "climate",
            gravity = "gravity",
            population = 0,
            diameter = 0.0,
            terrain = "terrain",
            url = "url"
        )
        val result = planet.toPresentation()
        assertEquals("name", result.name)
        assertEquals("climate", result.climate)
        assertEquals("gravity", result.gravity)
        assertEquals(0, result.population)
        assertEquals(0.0, result.diameter, 0.0)
        assertEquals("terrain", result.terrain)
        assertEquals("url", result.url)
    }
}