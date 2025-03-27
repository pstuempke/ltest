package com.stuempke.data.repository

import com.stuempke.core.domain.Error
import com.stuempke.core.domain.Result
import com.stuempke.data.MainCoroutineRule
import com.stuempke.data.RemotePlanetDataSource
import com.stuempke.data.di.AppDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlanetRepositoryImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: PlanetRepositoryImpl
    private lateinit var remoteDataSource: RemotePlanetDataSource

    private val planet = planetStub
    private val planetDto = planetDtoStub

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
                listOf(planetDto)
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
            coEvery { remoteDataSource.getPlanetByUrl(planet.url) } returns Result.Success(planetDto)

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
