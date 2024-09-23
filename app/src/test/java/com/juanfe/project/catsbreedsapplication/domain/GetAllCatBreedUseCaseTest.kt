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

class GetAllCatBreedUseCaseTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var catBreedRepository: CatBreedRepository

    private lateinit var getAllCatBreedUseCase: GetAllCatBreedUseCase

    @Before
    fun setUp() {
        getAllCatBreedUseCase = GetAllCatBreedUseCase(catBreedRepository)
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
                ),
                BreedModel(
                    "1",
                    "Breed1",
                    "Egypt",
                    "Active,Energetic,Independet",
                    "XASDXA",
                    "14-15"
                ),
                BreedModel(
                    "1",
                    "Breed1",
                    "Egypt",
                    "Active,Energetic,Independet",
                    "XASDXA",
                    "14-15"
                )
            )
            Mockito.`when`(catBreedRepository.getCatBreed(0))
                .thenReturn(Result.success(expectedCatBreed))

            // Act
            val result = getAllCatBreedUseCase(0)

            // Assert
            Assert.assertEquals(Result.success(expectedCatBreed), result)
        }

    @Test
    fun `invoke should return failure when repository call fails`() = runTest {
        val catId = "1"
        val exception = Exception("Null or empty")
        Mockito.`when`(catBreedRepository.getCatBreed(0))
            .thenReturn(Result.failure(exception))

        // Act
        val result = getAllCatBreedUseCase(0)

        // Assert
        Assert.assertEquals(Result.failure<BreedFullModel>(exception), result)
    }


}