package com.juanfe.project.catsbreedsapplication.ui.landing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juanfe.project.catsbreedsapplication.domain.BreedModel
import com.juanfe.project.catsbreedsapplication.domain.GetAllCatBreedUseCase
import com.juanfe.project.catsbreedsapplication.domain.SearchBreedUseCase
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class LandingViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var getAllCatBreedUseCase: GetAllCatBreedUseCase
    private lateinit var searchBreedUseCase: SearchBreedUseCase
    private lateinit var viewModel: LandingViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)

        getAllCatBreedUseCase = mock(GetAllCatBreedUseCase::class.java)
        runTest {
            val mockSearchResult = listOf(
                BreedModel(
                    "1",
                    "Breed1",
                    "Egypt",
                    "Active,Energetic,Independent",
                    "XASDXA",
                    "14 - 15"
                )
            )
            `when`(getAllCatBreedUseCase.invoke(0)).thenReturn(Result.success(mockSearchResult))
        }

        searchBreedUseCase = mock(SearchBreedUseCase::class.java)

        viewModel = LandingViewModel(getAllCatBreedUseCase, searchBreedUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchBreeds should update viewState`() = runTest {
        val mockSearchResult = listOf(
            BreedModel("1", "Breed1", "Egypt", "Active,Energetic,Independent", "XASDXA", "14 - 15")
        )

        `when`(searchBreedUseCase.invoke("abys")).thenReturn(Result.success(mockSearchResult))


        viewModel.searchBreeds("abys")

        // Esperar a que el flujo termine
        advanceUntilIdle()

        val state = viewModel.viewState.value
        assertNotNull(state)
    }

    @Test
    fun `reLoadGetCatBreed should update viewState`() = runTest {
        val mockSearchResult = listOf(
            BreedModel("1", "Breed1", "Egypt", "Active,Energetic,Independent", "XASDXA", "14 - 15")
        )
        `when`(searchBreedUseCase.invoke("abys")).thenReturn(Result.success(mockSearchResult))


        viewModel.reLoadGetCatBreed()

        // Esperar a que el flujo termine
        advanceUntilIdle()

        val state = viewModel.viewState.value
        assertNotNull(state)
    }


}
