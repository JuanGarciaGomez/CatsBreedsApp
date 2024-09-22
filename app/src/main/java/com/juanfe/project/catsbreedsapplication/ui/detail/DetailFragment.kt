package com.juanfe.project.catsbreedsapplication.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanfe.project.catsbreedsapplication.core.ex.loadCatBreadDetailImg
import com.juanfe.project.catsbreedsapplication.databinding.FragmentDetailBinding
import com.juanfe.project.catsbreedsapplication.domain.AttributesModel
import com.juanfe.project.catsbreedsapplication.domain.BreedFullModel
import com.juanfe.project.catsbreedsapplication.ui.detail.adapter.DetailAttributesAdapter
import com.juanfe.project.catsbreedsapplication.ui.detail.adapter.DetailTemperamentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    private val detailViewModel: DetailViewModel by viewModels()


    private lateinit var detailAttributesAdapter: DetailAttributesAdapter
    private lateinit var detailTemperamentAdapter: DetailTemperamentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        search()
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.viewState.collect {
                    when (it) {
                        DetailViewState.Error -> {}
                        DetailViewState.Loading -> {}
                        is DetailViewState.Success -> {
                            drawUi(it.breedModels)
                        }

                        null -> {}
                    }
                }
            }
        }
    }

    private fun drawUi(breedFullModel: BreedFullModel) {
        setUpAttributesRv(breedFullModel)
        setUpTemperamentsRv(breedFullModel)
        with(binding) {
            catBreedImg.loadCatBreadDetailImg(imgId = breedFullModel.imageId)
            catBreedName.text = breedFullModel.name
            data1.text = breedFullModel.lifeSpan
            data2.text = breedFullModel.origin
            catBreedId.text = breedFullModel.id
            description.text = breedFullModel.description
        }
    }

    private fun setUpTemperamentsRv(breedFullModel: BreedFullModel) {
        binding.catBreedTemperamentRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val list = breedFullModel.temperament.split(",")
        detailTemperamentAdapter = DetailTemperamentAdapter(list)
        binding.catBreedTemperamentRv.adapter = detailTemperamentAdapter
    }

    private fun setUpAttributesRv(breedFullModel: BreedFullModel) {

        val listAttributes = listOf(
            AttributesModel("AL", breedFullModel.affectionLevel),
            AttributesModel("AD", breedFullModel.adaptability),
            AttributesModel("IN", breedFullModel.intelligence),
            AttributesModel("EL", breedFullModel.energyLevel),
            AttributesModel("SN", breedFullModel.socialNeeds),
            AttributesModel("SF", breedFullModel.strangerFriendly),
            AttributesModel("DF", breedFullModel.dogFriendly),
            AttributesModel("CF", breedFullModel.childFriendly)
        )

        binding.catBreedAttributesRv.layoutManager = LinearLayoutManager(requireContext())

        detailAttributesAdapter = DetailAttributesAdapter(listAttributes) {
            Log.e("NAVEGAR", "para atras")
        }
        binding.catBreedAttributesRv.adapter = detailAttributesAdapter
    }

    private fun search() {
        detailViewModel.getCatBreedInformation(args.catBreedId)
    }


}