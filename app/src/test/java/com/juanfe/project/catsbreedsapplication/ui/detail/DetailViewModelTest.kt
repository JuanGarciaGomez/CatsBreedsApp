package com.juanfe.project.catsbreedsapplication.ui.detail

import app.cash.turbine.test
import com.juanfe.project.catsbreedsapplication.domain.AttributesModel
import com.juanfe.project.catsbreedsapplication.domain.BreedFullModel
import com.juanfe.project.catsbreedsapplication.domain.GetCatBreedInformationById
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var getCatBreedInformationById: GetCatBreedInformationById

    private lateinit var viewModel: DetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(getCatBreedInformationById)
    }

    @Test
    fun `getCatBreedInformation should emit Loading then Success when use case returns data`() =
        runTest {
            //Use AttributesModel for testing
            val attributesModel = AttributesModel("mock", 0)

            val catBreedId = "abys"
            val expectedCatBreed = BreedFullModel(
                "1",
                "Breed1",
                "Egypt",
                "Active and slaphappy cat",
                "Active,Energetic,Independet",
                "XASDXA",
                "14-15",
                3,
                2,
                4,
                1,
                4,
                3,
                5,
                8
            )
            Mockito.`when`(getCatBreedInformationById.invoke(catBreedId))
                .thenReturn(Result.success(expectedCatBreed))

            // Act & Assert
            viewModel.viewState.test {
                viewModel.getCatBreedInformation(catBreedId)

                // First emission should be Loading
                assertEquals(DetailViewState.Loading, awaitItem())

                // Second emission should be Success with the cat breed data
                assertEquals(DetailViewState.Success(expectedCatBreed), awaitItem())
            }
        }

    @Test
    fun `getCatBreedInformation should emit Loading then Error when use case returns failure`() =
        runTest {
            // Arrange
            val catBreedId = "1"
            Mockito.`when`(getCatBreedInformationById.invoke(catBreedId))
                .thenReturn(Result.failure(Exception("Error fetching data")))

            // Act & Assert
            viewModel.viewState.test {
                viewModel.getCatBreedInformation(catBreedId)

                // First emission should be Loading
                assertEquals(DetailViewState.Loading, awaitItem())

                // Second emission should be Error
                assertEquals(DetailViewState.Error, awaitItem())
            }
        }
}
