package com.juanfe.project.catsbreedsapplication.data.repository

import com.juanfe.project.catsbreedsapplication.domain.BreedModel

interface CatBreedRepository {

    suspend fun getCatBreed(nextPageId: Int): Result<List<BreedModel>>
    suspend fun searchBreed(query: String): Result<List<BreedModel>>

}