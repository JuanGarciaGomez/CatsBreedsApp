package com.juanfe.project.catsbreedsapplication.domain

import javax.inject.Inject

class GetAllCatBreedUseCase @Inject constructor(private val catBreedRepository: CatBreedRepository) {
    suspend operator fun invoke(nextPageId: Int) = catBreedRepository.getCatBreed(nextPageId = nextPageId)

}