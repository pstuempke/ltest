package com.stuempke.luzuatest.data

import com.stuempke.luzuatest.data.model.PlanetDto
import com.stuempke.luzuatest.domain.model.Planet

interface RemotePlanetDataSource {

    suspend fun getPlanets(): Result<List<PlanetDto>>

    suspend fun getPlanetByUrl(url: String): Result<PlanetDto>
}