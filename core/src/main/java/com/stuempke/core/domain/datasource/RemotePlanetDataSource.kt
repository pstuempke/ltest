package com.stuempke.core.domain.datasource

import com.stuempke.core.domain.Result
import com.stuempke.core.domain.Error
import com.stuempke.core.domain.model.Planet

interface RemotePlanetDataSource {

    suspend fun getPlanets(): Result<List<Planet>, Error.Remote>

    suspend fun getPlanetByUrl(url: String): Result<Planet, Error.Remote>
}