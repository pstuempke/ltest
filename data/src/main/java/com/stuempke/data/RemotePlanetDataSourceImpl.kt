package com.stuempke.data

import com.stuempke.core.domain.Error
import com.stuempke.core.domain.Result
import com.stuempke.core.domain.datasource.RemotePlanetDataSource
import com.stuempke.core.domain.model.Planet
import com.stuempke.data.model.PlanetDto
import com.stuempke.data.model.toDomain
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemotePlanetDataSourceImpl(private val client: HttpClient) : RemotePlanetDataSource {

    override suspend fun getPlanets(): Result<List<Planet>, Error.Remote> = safeCall {
        client.get("planets").body<PaginatedResponse<PlanetDto>>().results.map { it.toDomain() }
    }

    override suspend fun getPlanetByUrl(url: String): Result<Planet, Error.Remote> = safeCall {
        client.get(url).body<PlanetDto>().toDomain()
    }
}