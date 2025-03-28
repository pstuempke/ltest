package com.stuempke.core.repository

import com.stuempke.core.MainCoroutineRule
import com.stuempke.core.domain.Error
import com.stuempke.core.domain.Result
import com.stuempke.core.domain.repository.PlanetRepositoryImpl
import com.stuempke.core.domain.datasource.RemotePlanetDataSource
import com.stuempke.core.domain.AppDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlanetRepositoryImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: PlanetRepositoryImpl
    private lateinit var remoteDataSource: RemotePlanetDataSource

    private val planet = planetStub

    @Before
    fun setup() {
        remoteDataSource = mockk()
        val dispatchers = AppDispatchers(
            io = mainCoroutineRule.testDispatcher,
            default = mainCoroutineRule.testDispatcher,
            main = mainCoroutineRule.testDispatcher
        )
        repository = PlanetRepositoryImpl(remoteDataSource, dispatchers)
    }

    @Test
    fun `getPlanets should return success and cache planets`() =
        runTest(mainCoroutineRule.testDispatcher) {
            coEvery { remoteDataSource.getPlanets() } returns com.stuempke.core.domain.Result.Success(
                listOf(planet)
            )

            val result = repository.getPlanets()

            assertTrue(result is Result.Success)
            assertEquals(listOf(planet), (result.getOrNull()))

            // Now fetch again and make sure it comes from cache
            val singlePlanet = repository.getPlanet(planet.url)
            assertTrue(singlePlanet is Result.Success)
            assertEquals(planet, singlePlanet.getOrNull())
        }

    @Test
    fun `getPlanet should return from remote if not cached`() =
        runTest(mainCoroutineRule.testDispatcher) {
            coEvery { remoteDataSource.getPlanetByUrl(planet.url) } returns Result.Success(planet)

            val result = repository.getPlanet(planet.url)

            assertTrue(result is Result.Success)
            assertEquals(Result.Success(planet), (result as Result.Success))
        }

    @Test
    fun `getPlanet should return error if remote fails`() =
        runTest(mainCoroutineRule.testDispatcher) {
            coEvery { remoteDataSource.getPlanetByUrl(planet.url) } returns Result.Error(Error.Remote.SERVER)

            val result = repository.getPlanet(planet.url)

            assertTrue(result is Result.Error)
            assertEquals(Result.Error(Error.Remote.SERVER), result)
        }
}
