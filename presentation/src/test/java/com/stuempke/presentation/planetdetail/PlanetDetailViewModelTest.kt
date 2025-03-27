package com.stuempke.presentation.planetdetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.stuempke.core.domain.Error
import com.stuempke.core.domain.PlanetRepository
import io.mockk.coEvery
import io.mockk.mockk
import com.stuempke.core.domain.Result
import com.stuempke.core.domain.model.Planet
import com.stuempke.presentation.MainCoroutineRule
import com.stuempke.presentation.model.toPresentation
import com.stuempke.presentation.navigation.NavigationManager
import io.mockk.coVerify
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PlanetDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val savedStateHandle = SavedStateHandle(mapOf("url" to "url"))
    private val navigationManager = mockk<NavigationManager>(relaxed = true)
    private val planetRepository = mockk<PlanetRepository>(relaxed = true)
    private lateinit var sut: PlanetDetailViewModel

    @Test
    fun `test view model returns content`() = runTest(mainCoroutineRule.testDispatcher) {
        // Arrange: Create a test planet with expected properties
        val testPlanet = planetStub

        // Arrange: Configure the repository mock
        coEvery { planetRepository.getPlanet("url") } returns Result.Success(testPlanet)

        // Act: Instantiate the ViewModel
        sut = PlanetDetailViewModel(
            savedStateHandle = savedStateHandle,
            planetRepository = planetRepository,
            navigationManager = navigationManager
        )

        // Assert: Use Turbine to collect state emissions
        sut.state.test {
            // First emission should be Loading
            assertTrue(awaitItem() is PlanetDetailViewModel.ViewState.Loading)

            // Next emission should be Content with the expected planet
            val contentState = awaitItem() as PlanetDetailViewModel.ViewState.Content
            assertEquals(testPlanet.toPresentation(), contentState.planet)

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `test view model returns error`() = runTest(mainCoroutineRule.testDispatcher) {
        // Arrange: Configure the repository mock
        val error = Error.Remote.SERVER
        coEvery { planetRepository.getPlanet("url") } returns Result.Error(error)

        // Act: Instantiate the ViewModel
        sut = PlanetDetailViewModel(
            savedStateHandle = savedStateHandle,
            planetRepository = planetRepository,
            navigationManager = navigationManager
        )

        // Assert: Use Turbine to collect state emissions
        sut.state.test {
            // First emission should be Loading
            assertTrue(awaitItem() is PlanetDetailViewModel.ViewState.Loading)

            // Next emission should be Error with the expected message
            val errorState = awaitItem() as PlanetDetailViewModel.ViewState.Error
            assertEquals(error.toString(), errorState.message)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test view model retry`() = runTest(mainCoroutineRule.testDispatcher) {
        // Arrange: Configure the repository mock
        val error = Error.Remote.SERVER
        coEvery { planetRepository.getPlanet("url") } returns Result.Error(error)

        // Act: Instantiate the ViewModel
        sut = PlanetDetailViewModel(
            savedStateHandle = savedStateHandle,
            planetRepository = planetRepository,
            navigationManager = navigationManager
        )

        // Assert: Use Turbine to collect state emissions
        sut.state.test {
            // First emission should be Loading
            assertTrue(awaitItem() is PlanetDetailViewModel.ViewState.Loading)

            // Next emission should be Error with the expected message
            val errorState = awaitItem() as PlanetDetailViewModel.ViewState.Error
            assertEquals(error.toString(), errorState.message)

            // Act: Retry the action
            sut.onViewAction(PlanetDetailViewModel.ViewAction.Retry)

            advanceUntilIdle()
            // Assert: Next emission should be Loading
            assertTrue(awaitItem() is PlanetDetailViewModel.ViewState.Loading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test view model back`() = runTest(mainCoroutineRule.testDispatcher) {
        // Arrange: Create a test planet with expected properties
        val testPlanet = planetStub

        // Arrange: Configure the repository mock
        coEvery { planetRepository.getPlanet("url") } returns Result.Success(testPlanet)

        // Act: Instantiate the ViewModel
        sut = PlanetDetailViewModel(
            savedStateHandle = savedStateHandle,
            planetRepository = planetRepository,
            navigationManager = navigationManager
        )

        // Assert: Use Turbine to collect state emissions
        sut.state.test {

            // Act: Navigate back
            sut.onViewAction(PlanetDetailViewModel.ViewAction.Back)

            advanceUntilIdle()
            // Assert: NavigationManager should have been called

            coVerify(exactly = 1) { navigationManager.popBackStack() }

            cancelAndIgnoreRemainingEvents()
        }
    }
}

val planetStub = Planet(
    name = "name",
    climate = "climate",
    gravity = "gravity",
    population = 0,
    diameter = 0.0,
    terrain = "terrain",
    url = "url"
)
