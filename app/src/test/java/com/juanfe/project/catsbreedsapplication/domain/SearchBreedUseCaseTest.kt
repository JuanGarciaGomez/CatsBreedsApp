package com.juanfe.project.catsbreedsapplication.domain

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class SearchBreedUseCaseTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var catBreedRepository: CatBreedRepository

    private lateinit var searchBreedUseCase: SearchBreedUseCase

    @Before
    fun setUp() {
        searchBreedUseCase = SearchBreedUseCase(catBreedRepository)
    }


    @Test
    fun `invoke should return cat breed information when repository call is successful`() =
        runTest {
            val expectedCatBreed = listOf(
                BreedModel(
                    "1",
                    "Breed1",
                    "Egypt",
                    "Active,Energetic,Independet",
                    "XASDXA",
                    "14-15"
                )
            )
            Mockito.`when`(catBreedRepository.searchBreed("abdy"))
                .thenReturn(Result.success(expectedCatBreed))

            // Act
            val result = searchBreedUseCase("abdy")

            // Assert
            Assert.assertEquals(Result.success(expectedCatBreed), result)
        }

    @Test
    fun `invoke should return failure when repository call fails`() = runTest {
        // Arrange
        val catId = "1"
        val exception = Exception("Null or empty")
        Mockito.`when`(catBreedRepository.searchBreed("1"))
            .thenReturn(Result.failure(exception))

        // Act
        val result = searchBreedUseCase(catId)

        // Assert
        Assert.assertEquals(Result.failure<BreedFullModel>(exception), result)
    }


}