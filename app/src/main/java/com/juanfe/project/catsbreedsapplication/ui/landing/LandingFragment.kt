package com.juanfe.project.catsbreedsapplication.ui.landing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanfe.project.catsbreedsapplication.R
import com.juanfe.project.catsbreedsapplication.core.dialog.LoadingDialog
import com.juanfe.project.catsbreedsapplication.databinding.FragmentLandingBinding
import com.juanfe.project.catsbreedsapplication.ui.landing.adapter.CatBreedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LandingFragment : Fragment() {

    private var _binding: FragmentLandingBinding? = null
    private val binding get() = _binding!!

    private lateinit var catBreedAdapter: CatBreedAdapter

    private val landingViewModel: LandingViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initSearch()
        initObservers()
        setUpRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    performSearch(it)
                    val imm =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(searchView.windowToken, 0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                landingViewModel.reLoadGetCatBreed()
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun performSearch(query: String) {
        landingViewModel.searchBreeds(query)
    }

    private fun initSearch() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.topAppBar)
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                landingViewModel.viewState.collect { viewState ->
                    updateUi(viewState)
                }
            }
        }
    }

    private fun updateUi(viewState: LandingViewState) {
        when (viewState) {
            LandingViewState.Error -> {
                LoadingDialog.dismiss()
                binding.fetchingContainer.visibility = View.GONE
            }

            LandingViewState.Loading -> {
                LoadingDialog.create(requireContext())
                catBreedAdapter.updateList(listOf())
            }

            is LandingViewState.Success -> {
                catBreedAdapter.updateList(viewState.breedModels)
                binding.fetchingContainer.visibility =
                    if (viewState.isFetching) View.VISIBLE else View.GONE
                LoadingDialog.dismiss()
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.catBreedRv.layoutManager = LinearLayoutManager(requireContext())

        catBreedAdapter = CatBreedAdapter(listOf()) {
            //TODO NAVIGATION TO OTHER FRAGMENT
        }
        binding.catBreedRv.adapter = catBreedAdapter
        binding.catBreedRv.addOnScrollListener(landingViewModel.onScrollListener)
    }

}