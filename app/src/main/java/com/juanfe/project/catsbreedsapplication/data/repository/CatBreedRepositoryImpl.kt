package com.juanfe.project.catsbreedsapplication.data.repository

import com.juanfe.project.catsbreedsapplication.data.network.BreedService
import com.juanfe.project.catsbreedsapplication.data.network.toDomain
import com.juanfe.project.catsbreedsapplication.domain.BreedModel
import com.juanfe.project.catsbreedsapplication.domain.CatBreedRepository
import javax.inject.Inject


class CatBreedRepositoryImpl @Inject constructor(private val breedService: BreedService) :
    CatBreedRepository {
    override suspend fun getCatBreed(nextPageId: Int): Result<List<BreedModel>> = runCatching {
        val response = breedService.getCatBreedList(page = nextPageId)
        val body = response.body()
        if (body.isNullOrEmpty()) {
            throw Exception("Null or empty")
        } else {
            body.map { it.toDomain() }
        }
    }

    override suspend fun searchBreed(query: String): Result<List<BreedModel>>  = runCatching {
        val response = breedService.searchBreed(query)
        val body = response.body()
        if (body.isNullOrEmpty()) {
            throw Exception("Null or empty")
        } else {
            body.map { it.toDomain() }
        }
    }
}
