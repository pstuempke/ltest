package com.stuempke.luzuatest.data

import com.stuempke.luzuatest.domain.model.Planet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import timber.log.Timber

class RemotePlanetDataSourceImpl(private val client: HttpClient) : RemotePlanetDataSource {

    override suspend fun getPlanets(): Result<List<Planet>> = runCatching {
        client.get("planets").body<PaginatedResponse<Planet>>().results
    }

    override suspend fun getPlanetByUrl(url: String): Result<Planet> = runCatching {
        client.get(url).body<Planet>()
    }
}