package com.juanfe.project.catsbreedsapplication.domain

import javax.inject.Inject

class SearchBreedUseCase @Inject constructor(private val catBreedRepository: CatBreedRepository) {
    suspend operator fun invoke(query: String) = catBreedRepository.searchBreed(query = query)

}


