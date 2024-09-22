package com.juanfe.project.catsbreedsapplication.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanfe.project.catsbreedsapplication.domain.GetCatBreedInformationById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCatBreedInformationById: GetCatBreedInformationById,
) : ViewModel() {

    private val _viewState = MutableStateFlow<DetailViewState?>(null)
    val viewState: StateFlow<DetailViewState?>
        get() = _viewState

    fun getCatBreedInformation(catBreedId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value = DetailViewState.Loading
            getCatBreedInformationById.invoke(catBreedId).fold(onSuccess = { catBreed ->
                _viewState.value = DetailViewState.Success(catBreed)
            }, onFailure = {
                _viewState.value = DetailViewState.Error
                it.printStackTrace()
            })
        }

    }


}