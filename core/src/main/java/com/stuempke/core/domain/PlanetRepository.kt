package com.stuempke.core.domain

import com.stuempke.core.domain.model.Planet

interface PlanetRepository {

    suspend fun getPlanets(): Result<List<Planet>, Error>

    suspend fun getPlanet(url: String): Result<Planet, Error>

}