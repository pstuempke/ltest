package com.stuempke.presentation.planetlist

import app.cash.turbine.test
import com.stuempke.core.domain.PlanetRepository
import com.stuempke.presentation.MainCoroutineRule
import com.stuempke.presentation.navigation.NavigationManager
import io.mockk.coEvery
import com.stuempke.core.domain.Result
import com.stuempke.core.domain.Error
import com.stuempke.presentation.model.planetUiStub
import com.stuempke.presentation.model.toPresentation
import com.stuempke.presentation.navigation.Route
import com.stuempke.presentation.planetdetail.planetStub
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PlanetListViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private val navigationManager = mockk<NavigationManager>(relaxed = true)
    private val planetRepository = mockk<PlanetRepository>(relaxed = true)

    private lateinit var sut: PlanetListViewModel

    @Test
    fun `test view model returns list after loading`() = runTest(mainCoroutineRule.testDispatcher) {

        // Arrange: Create a test planet with expected properties
        val planet = planetStub

        // Arrange: Configure the repository mock
        coEvery { planetRepository.getPlanets() } returns Result.Success(listOf(planet))

        // Act: Instantiate the ViewModel
        sut = PlanetListViewModel(
            planetRepository = planetRepository,
            navigationManager = navigationManager
        )

        // Assert: Use Turbine to collect state emissions
        sut.state.test {
            // First emission should be Loading
            assertTrue(awaitItem() is PlanetListViewModel.ViewState.Loading)

            // Next emission should be Content with the expected planet
            val contentState = awaitItem() as PlanetListViewModel.ViewState.Content
            assertEquals(listOf(planet.toPresentation()), contentState.planets)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test view model navigates to detail on planet selected`() =
        runTest(mainCoroutineRule.testDispatcher) {

            // Arrange: Create a test planet with expected properties
            val planet = planetUiStub

            // Act: Instantiate the ViewModel
            sut = PlanetListViewModel(
                planetRepository = planetRepository,
                navigationManager = navigationManager
            )

            // Act: Trigger the navigation action
            sut.onViewAction(PlanetListViewModel.ViewAction.PlanetSelected(planet))

            advanceUntilIdle()

            // Assert: Verify that the navigation manager was called with the expected route
            coVerify { navigationManager.navigate(Route.PlanetDetails(planet.url)) }
        }


    @Test
    fun `test view model returns no internet  error when repo fails with no internet`() =
        runTest(mainCoroutineRule.testDispatcher) {

            coEvery { planetRepository.getPlanets() } returns Result.Error(Error.Remote.NO_INTERNET)

            sut = PlanetListViewModel(
                planetRepository = planetRepository,
                navigationManager = navigationManager
            )

            sut.state.test {
                assertTrue(awaitItem() is PlanetListViewModel.ViewState.Loading)
                val errorState = awaitItem() as PlanetListViewModel.ViewState.Error
                assertEquals(Error.Remote.NO_INTERNET.toString(), errorState.message)
                cancelAndIgnoreRemainingEvents()
            }
        }
}
