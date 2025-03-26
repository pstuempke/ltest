package com.stuempke.luzuatest.data

import com.stuempke.luzuatest.data.model.PlanetDto
import com.stuempke.luzuatest.domain.Error
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import com.stuempke.luzuatest.domain.Result


class RemotePlanetDataSourceImpl(private val client: HttpClient) : RemotePlanetDataSource {

    override suspend fun getPlanets(): Result<List<PlanetDto>, Error.Remote> = safeCall {
            client.get("planets").body<PaginatedResponse<PlanetDto>>().results
        }

    override suspend fun getPlanetByUrl(url: String): Result<PlanetDto, Error.Remote> = safeCall {
            client.get(url).body<PlanetDto>()
        }
}