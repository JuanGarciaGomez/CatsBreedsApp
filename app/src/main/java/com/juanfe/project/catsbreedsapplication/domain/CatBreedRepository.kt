package com.juanfe.project.catsbreedsapplication.domain

interface CatBreedRepository {

    suspend fun getCatBreed(nextPageId: Int): Result<List<BreedModel>>
    suspend fun searchBreed(query: String): Result<List<BreedModel>>
    suspend fun getCatBreedInformationById(catId: String): Result<BreedFullModel>

}