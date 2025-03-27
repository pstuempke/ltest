package com.stuempke.data

import com.stuempke.data.model.PlanetDto
import com.stuempke.core.domain.Result
import com.stuempke.core.domain.Error

interface RemotePlanetDataSource {

    suspend fun getPlanets(): Result<List<PlanetDto>, Error.Remote>

    suspend fun getPlanetByUrl(url: String): Result<PlanetDto, Error.Remote>
}