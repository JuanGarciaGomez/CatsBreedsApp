package com.juanfe.project.catsbreedsapplication.ui.detail

import com.juanfe.project.catsbreedsapplication.domain.BreedFullModel

sealed class DetailViewState() {
    data object Loading : DetailViewState()
    data object Error : DetailViewState()
    data class Success(val breedModels: BreedFullModel) : DetailViewState()

}