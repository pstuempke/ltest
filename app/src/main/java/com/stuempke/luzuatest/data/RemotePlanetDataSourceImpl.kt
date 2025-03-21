package com.stuempke.luzuatest.data

import com.stuempke.luzuatest.domain.model.Planet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import timber.log.Timber

class RemotePlanetDataSourceImpl(private val client: HttpClient) : RemotePlanetDataSource {
    override suspend fun getPlanets(): Result<List<Planet>> = runCatching {
        val response = client.get("planets")
        val planetsResponse = response.body<PaginatedResponse<Planet>>()
        Timber.d("Received planets: ${planetsResponse.results}")
        planetsResponse.results
    }

    override suspend fun getPlanetByName(name: String): Result<Planet> {
        TODO("Not yet implemented")
    }
}