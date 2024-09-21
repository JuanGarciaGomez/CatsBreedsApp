package com.juanfe.project.catsbreedsapplication.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
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
        initObservers()
        initCallService()
        setUpRecyclerView()
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
            LandingViewState.Error -> LoadingDialog.dismiss()

            LandingViewState.Loading -> {
                LoadingDialog.create(requireContext())
                catBreedAdapter.updateList(listOf())
            }

            is LandingViewState.Success -> {
                catBreedAdapter.updateList(viewState.breedModels)
                LoadingDialog.dismiss()
            }
        }
    }

    private fun initCallService() {
        landingViewModel.getCatBreedList()
    }

    private fun setUpRecyclerView() {
        val layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean = true
        }
        binding.catBreedRv.layoutManager = layoutManager

        catBreedAdapter = CatBreedAdapter(listOf()) {

        }
        binding.catBreedRv.adapter = catBreedAdapter
    }

}