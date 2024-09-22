package com.juanfe.project.catsbreedsapplication.ui.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juanfe.project.catsbreedsapplication.data.repository.CatBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(private val mainRepository: CatBreedRepository) :
    ViewModel() {

    private val _viewState = MutableStateFlow<LandingViewState>(LandingViewState.Loading)
    val viewState: StateFlow<LandingViewState> = _viewState

    private var nextPageId = 0

    init {
        getCatBreedList()
    }

    private fun getCatBreedList() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getCatBreed(nextPageId).fold(onSuccess = { catBreedList ->
                nextPageId++
                val state = _viewState.value
                val list = if (state is LandingViewState.Success) {
                    state.breedModels.plus(catBreedList)
                } else {
                    catBreedList
                }.filter {
                    it.name !in listOf("Turkish Van", "Ragdoll", "Chartreux")
                }
                _viewState.value = LandingViewState.Success(list, false)

            }, onFailure = {
                it.printStackTrace()
                _viewState.value = LandingViewState.Error
                nextPageId = -1
            })
        }
    }

    fun searchBreeds(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value = LandingViewState.Loading
            nextPageId = -1
            mainRepository.searchBreed(query).fold(onSuccess = { searchResult ->
                _viewState.value = LandingViewState.Success(searchResult.filter {
                    it.name !in listOf("Turkish Van", "Ragdoll", "Chartreux")
                }, false)
            }, onFailure = {
                _viewState.value = LandingViewState.Error
                it.printStackTrace()
            })
        }
    }


    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val state = _viewState.value
            if (state !is LandingViewState.Success || state.isFetching) {
                return
            }

            // Detect layout manager
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            // Load more if the last item is visible
            if (
                (visibleItemCount + firstVisibleItemPosition >= totalItemCount)
                && firstVisibleItemPosition >= 0
            ) {
                fetch()
            }
        }
    }

    private fun fetch() {
        val state = _viewState.value
        if (state !is LandingViewState.Success || (state.isFetching)) {
            return
        }

        if (nextPageId != -1) {
            _viewState.update { state.copy(isFetching = true) }
            getCatBreedList()
        }
    }

    fun reLoadGetCatBreed() {
        _viewState.value = LandingViewState.Loading
        nextPageId = 0
        getCatBreedList()
    }

}