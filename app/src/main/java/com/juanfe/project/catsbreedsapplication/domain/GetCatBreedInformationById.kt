package com.juanfe.project.catsbreedsapplication.domain

import javax.inject.Inject

class GetCatBreedInformationById @Inject constructor(private val catBreedRepository: CatBreedRepository) {
    suspend operator fun invoke(catId: String) = catBreedRepository.getCatBreedInformationById(catId)

}