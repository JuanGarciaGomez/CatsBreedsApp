package com.juanfe.project.catsbreedsapplication.domain

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class GetCatBreedInformationByIdTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var catBreedRepository: CatBreedRepository

    private lateinit var getCatBreedInformationById: GetCatBreedInformationById

    @Before
    fun setUp() {
        getCatBreedInformationById = GetCatBreedInformationById(catBreedRepository)
    }

    @Test
    fun `invoke should return cat breed information when repository call is successful`() =
        runTest {
            // Arrange
            val catId = "1"
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
            Mockito.`when`(catBreedRepository.getCatBreedInformationById(catId))
                .thenReturn(Result.success(expectedCatBreed))

            // Act
            val result = getCatBreedInformationById(catId)

            // Assert
            assertEquals(Result.success(expectedCatBreed), result)
        }

    @Test
    fun `invoke should return failure when repository call fails`() = runTest {
        // Arrange
        val catId = "1"
        val exception = Exception("Error fetching cat breed information")
        Mockito.`when`(catBreedRepository.getCatBreedInformationById(catId))
            .thenReturn(Result.failure(exception))

        // Act
        val result = getCatBreedInformationById(catId)

        // Assert
        assertEquals(Result.failure<BreedFullModel>(exception), result)
    }
}
