package com.stuempke.luzuatest.domain

import com.stuempke.luzuatest.domain.model.Planet

interface PlanetRepository {

    suspend fun getPlanets(): Result<List<Planet>>

    suspend fun getPlanet(url: String): Result<Planet>

}