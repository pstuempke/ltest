package com.stuempke.data

import com.stuempke.data.model.PlanetDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class RemotePlanetDataSourceImpl(private val client: HttpClient) : RemotePlanetDataSource {

    override suspend fun getPlanets(): com.stuempke.core.domain.Result<List<PlanetDto>, com.stuempke.core.domain.Error.Remote> = safeCall {
            client.get("planets").body<PaginatedResponse<PlanetDto>>().results
        }

    override suspend fun getPlanetByUrl(url: String): com.stuempke.core.domain.Result<PlanetDto, com.stuempke.core.domain.Error.Remote> = safeCall {
            client.get(url).body<PlanetDto>()
        }
}