package com.juanfe.project.catsbreedsapplication.data.repository

import com.juanfe.project.catsbreedsapplication.data.network.BreedService
import com.juanfe.project.catsbreedsapplication.data.network.toDomain
import com.juanfe.project.catsbreedsapplication.domain.BreedModel
import javax.inject.Inject


class CatBreedRepositoryImpl @Inject constructor(private val breedService: BreedService) :
    CatBreedRepository {
    override suspend fun getCatBreed(): Result<List<BreedModel>> = runCatching {
        val response = breedService.getCatBreedList()
        val body = response.body()
        if (body.isNullOrEmpty()) {
            throw Exception("Null or empty")
        } else {
            body.map { it.toDomain() }
        }
    }
}
