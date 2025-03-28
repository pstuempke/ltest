package com.stuempke.core.domain.repository

import com.stuempke.core.domain.AppDispatchers
import com.stuempke.core.domain.Error
import com.stuempke.core.domain.PlanetRepository
import com.stuempke.core.domain.Result
import com.stuempke.core.domain.datasource.RemotePlanetDataSource
import com.stuempke.core.domain.map
import com.stuempke.core.domain.mapList
import com.stuempke.core.domain.model.Planet
import kotlinx.coroutines.withContext

import java.util.concurrent.ConcurrentHashMap


class PlanetRepositoryImpl(
    private val remotePlanetDataSource: RemotePlanetDataSource,
    private val appDispatchers: AppDispatchers,
) :
    PlanetRepository {

    // Instead of implementing a full database I just added a small in-memory cache
    private val planetCache = ConcurrentHashMap<String, Planet>()


    override suspend fun getPlanets(): Result<List<Planet>, Error> =
        withContext(appDispatchers.io) {
            remotePlanetDataSource.getPlanets()
                .also { result ->
                    result.getOrNull()?.let { planets ->
                        addToCache(*planets.toTypedArray())
                    }
                }
        }

    private fun addToCache(vararg planets: Planet?) {
        planetCache.putAll(planets.filterNotNull().associateBy { it.url })
    }

    override suspend fun getPlanet(url: String): Result<Planet, Error> =
        withContext(appDispatchers.io) {
            planetCache[url]?.let {
                Result.Success(it)
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
