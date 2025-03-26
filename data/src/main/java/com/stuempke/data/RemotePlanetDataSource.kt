package com.stuempke.data

interface RemotePlanetDataSource {

    suspend fun getPlanets(): com.stuempke.core.domain.Result<List<com.stuempke.data.model.PlanetDto>, com.stuempke.core.domain.Error.Remote>

    suspend fun getPlanetByUrl(url: String): com.stuempke.core.domain.Result<com.stuempke.data.model.PlanetDto, com.stuempke.core.domain.Error.Remote>
}