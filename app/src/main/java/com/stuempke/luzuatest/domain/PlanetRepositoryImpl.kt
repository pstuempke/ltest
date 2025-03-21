package com.stuempke.luzuatest.domain

import com.stuempke.luzuatest.data.RemotePlanetDataSource
import com.stuempke.luzuatest.domain.model.Planet


class PlanetRepositoryImpl(private val remotePlanetDataSource: RemotePlanetDataSource) :
    PlanetRepository {

    override suspend fun getPlanets(): Result<List<Planet>> {
        return remotePlanetDataSource.getPlanets()
    }

    override suspend fun getPlanet(id: String): Result<Planet> {
        return remotePlanetDataSource.getPlanet(id)
    }

}