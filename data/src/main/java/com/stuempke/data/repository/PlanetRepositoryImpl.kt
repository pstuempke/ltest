package com.stuempke.data.repository

import com.stuempke.core.domain.Error
import com.stuempke.core.domain.PlanetRepository
import com.stuempke.core.domain.Result
import com.stuempke.core.domain.map
import com.stuempke.core.domain.mapList
import com.stuempke.data.model.toDomain
import com.stuempke.core.domain.model.Planet
import com.stuempke.data.RemotePlanetDataSource
import com.stuempke.data.di.AppDispatchers
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
            remotePlanetDataSource.getPlanets().mapList { it.toDomain() }
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
                val result = remotePlanetDataSource.getPlanetByUrl(url).map { it.toDomain() }
                result.also { res ->
                    res.getOrNull()?.let { planet ->
                        addToCache(planet)
                    }
                }
            }
        }
}