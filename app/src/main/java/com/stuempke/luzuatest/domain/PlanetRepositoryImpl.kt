package com.stuempke.luzuatest.domain

import com.stuempke.luzuatest.data.RemotePlanetDataSource
import com.stuempke.luzuatest.domain.model.Planet
import java.util.concurrent.ConcurrentHashMap


class PlanetRepositoryImpl(private val remotePlanetDataSource: RemotePlanetDataSource) :
    PlanetRepository {

    // Instead of implementing a full database I just added a small in-memory cache
    private val planetCache = ConcurrentHashMap<String, Planet>()

    override suspend fun getPlanets(): Result<List<Planet>> {
        return remotePlanetDataSource.getPlanets().also { result ->
            result.getOrNull()?.let { planets ->
                addToCache(*planets.toTypedArray())
            }
        }
    }

    private fun addToCache(vararg planets: Planet?) {
        planetCache.putAll(planets.filterNotNull().associateBy { it.url })
    }

    override suspend fun getPlanet(url: String): Result<Planet> {
        return planetCache[url]?.let {
            Result.success(it)
        } ?: run {
            val result = remotePlanetDataSource.getPlanetByUrl(url)
            result.also { res ->
                res.getOrNull()?.let { planet ->
                    addToCache(planet)
                }
            }
        }
    }

}