package com.juanfe.project.catsbreedsapplication.ui.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanfe.project.catsbreedsapplication.data.repository.CatBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(private val mainRepository: CatBreedRepository) :
    ViewModel() {

    private val _viewState = MutableStateFlow<LandingViewState>(LandingViewState.Loading)
    val viewState: StateFlow<LandingViewState> = _viewState


    fun getCatBreedList() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getCatBreed().fold(onSuccess = { catBreedList ->
                _viewState.value = LandingViewState.Success(catBreedList)
                val state = _viewState.value
                if (state is LandingViewState.Success){
                    state.breedModels.plus(catBreedList)
                }
            }, onFailure = {
                _viewState.value = LandingViewState.Error
            })
        }
    }

    fun searchBreeds(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.searchBreed(query).fold(onSuccess = { searchResult ->
                _viewState.value = LandingViewState.Success(searchResult)
            }, onFailure = {
                _viewState.value = LandingViewState.Error
            })
        }
    }

}