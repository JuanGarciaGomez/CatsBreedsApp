package com.juanfe.project.catsbreedsapplication.data.repository

import com.juanfe.project.catsbreedsapplication.data.network.BreedService
import com.juanfe.project.catsbreedsapplication.data.network.response.BreedFullResponse
import com.juanfe.project.catsbreedsapplication.data.network.response.BreedResponse
import com.juanfe.project.catsbreedsapplication.data.network.toDomain
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response


class CatBreedRepositoryImplTest {

    private lateinit var breedService: BreedService
    private lateinit var catBreedRepository: CatBreedRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        breedService = mock(BreedService::class.java)
        catBreedRepository = CatBreedRepositoryImpl(breedService)
    }

    @Test
    fun `getCatBreed should return list of breeds`() = runBlocking {
        val mockResponse = listOf(
            BreedResponse(
                "1",
                "Breed1",
                "Egypt",
                "Active,Energetic,Independet",
                "XASDXA",
                "14 - 15"
            ),
            BreedResponse(
                "2",
                "Breed2",
                "Egypt",
                "Active,Energetic,Independet",
                "XASDXA",
                "14 - 15"
            )
        )
        `when`(breedService.getCatBreedList()).thenReturn(Response.success(mockResponse))


        val result = catBreedRepository.getCatBreed(0)

        assertTrue(result.isSuccess)
        assertEquals(mockResponse.map { it.toDomain() }, result.getOrNull())
    }

    @Test
    fun `searchBreed should return list of breeds`() = runBlocking {
        val mockResponse = listOf(
            BreedResponse(
                "1",
                "Breed1",
                "Egypt",
                "Active,Energetic,Independet",
                "XASDXA",
                "14 - 15"
            ),
        )
        `when`(breedService.searchBreed("Breed1")).thenReturn(Response.success(mockResponse))

        val result = catBreedRepository.searchBreed("Breed1")

        assertTrue(result.isSuccess)
        assertEquals(mockResponse.map { it.toDomain() }, result.getOrNull())

    }

    @Test
    fun `searchBreed should return empty list`() = runBlocking {
        val mockEmptyResponse = listOf<BreedResponse>()

        `when`(breedService.searchBreed("1")).thenReturn(Response.success(mockEmptyResponse))

        val result = catBreedRepository.searchBreed("1")

        assertTrue(result.isFailure)
        assertEquals(Exception("Null or empty").message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `getCatBreedInformationById should return breed information`() = runBlocking {
        val mockResponse = BreedFullResponse(
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
        `when`(breedService.getCatBreedInformationById("1")).thenReturn(
            Response.success(
                mockResponse
            )
        )

        val result = catBreedRepository.getCatBreedInformationById("1")

        assertTrue(result.isSuccess)
        assertEquals(mockResponse.toDomain(), result.getOrNull())
    }
}
