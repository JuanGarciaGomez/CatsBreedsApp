package com.juanfe.project.catsbreedsapplication.ui.landing

import com.juanfe.project.catsbreedsapplication.domain.BreedModel

sealed class LandingViewState() {
    data object Loading : LandingViewState()
    data object Error : LandingViewState()
    data class Success(val breedModels: List<BreedModel>) : LandingViewState()

}