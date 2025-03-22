package com.stuempke.luzuatest.data

import com.stuempke.luzuatest.domain.model.Planet

interface RemotePlanetDataSource {

    suspend fun getPlanets(): Result<List<Planet>>

    suspend fun getPlanetByUrl(url: String): Result<Planet>
}