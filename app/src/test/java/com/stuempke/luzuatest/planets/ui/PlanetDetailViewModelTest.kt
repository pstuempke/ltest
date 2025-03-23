package com.stuempke.luzuatest.planets.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.stuempke.luzuatest.domain.PlanetRepository
import com.stuempke.luzuatest.domain.model.Planet
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PlanetDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val savedStateHandle = SavedStateHandle(mapOf("url" to "url"))
    private val planetRepository = mockk<PlanetRepository>(relaxed = true)
    private lateinit var sut: PlanetDetailViewModel

    @Before
    fun setUp() {
    }

    @Test
    fun `test view model returns content`() = runTest(mainCoroutineRule.testDispatcher) {
        // Arrange: Create a test planet with expected properties
        val testPlanet = mockk<Planet>(relaxed = true) {
            every { name } returns "Test Planet"
            every { url } returns "url"
        }

        // Arrange: Configure the repository mock
        coEvery { planetRepository.getPlanet("url") } returns Result.success(testPlanet)

        // Act: Instantiate the ViewModel
        sut = PlanetDetailViewModel(savedStateHandle, planetRepository)

        // Assert: Use Turbine to collect state emissions
        sut.state.test {
            // First emission should be Loading
            assertTrue(awaitItem() is PlanetDetailViewModel.ViewState.Loading)

            // Next emission should be Content with the expected planet
            val contentState = awaitItem() as PlanetDetailViewModel.ViewState.Content
            assertEquals("Test Planet", contentState.planet.name)
            assertEquals("url", contentState.planet.url)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
