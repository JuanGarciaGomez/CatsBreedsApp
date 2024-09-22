package com.juanfe.project.catsbreedsapplication.domain

import com.juanfe.project.catsbreedsapplication.domain.BreedModel

interface CatBreedRepository {

    suspend fun getCatBreed(nextPageId: Int): Result<List<BreedModel>>
    suspend fun searchBreed(query: String): Result<List<BreedModel>>

}