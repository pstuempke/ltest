package com.stuempke.luzuatest.data

import com.stuempke.luzuatest.domain.model.Planet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemotePlanetDataSourceImpl(private val client: HttpClient) : RemotePlanetDataSource {
    override suspend fun getPlanets(): Result<List<Planet>> = kotlin.runCatching {
        val response = client.get("")
        val planets = response.body<List<Planet>>()
        planets
    }

    override suspend fun getPlanet(id: String): Result<Planet> {
        TODO("Not yet implemented")
    }
}