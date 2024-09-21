package com.juanfe.project.catsbreedsapplication.data.repository

import com.juanfe.project.catsbreedsapplication.domain.BreedModel

interface CatBreedRepository {

    suspend fun getCatBreed(): Result<List<BreedModel>>

}