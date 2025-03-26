package com.stuempke.luzuatest.data

import com.stuempke.luzuatest.data.model.PlanetDto
import com.stuempke.luzuatest.domain.Error
import com.stuempke.luzuatest.domain.Result

interface RemotePlanetDataSource {

    suspend fun getPlanets(): Result<List<PlanetDto>, Error.Remote>

    suspend fun getPlanetByUrl(url: String): Result<PlanetDto, Error.Remote>
}