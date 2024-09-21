package com.juanfe.project.catsbreedsapplication.ui.landing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.juanfe.project.catsbreedsapplication.core.ex.loadCatBreadImg
import com.juanfe.project.catsbreedsapplication.data.network.response.BreedResponse
import com.juanfe.project.catsbreedsapplication.databinding.ItemCatBreedBinding
import com.juanfe.project.catsbreedsapplication.domain.BreedModel

//BreedResponse debe ser reemplazado por un modelo para la UI

class CatBreedAdapter(
    private var list: List<BreedModel>,
    private val onItemSelected: (BreedModel) -> Unit
) : RecyclerView.Adapter<CatBreedAdapter.MyViewHolder>() {


    fun updateList(newList: List<BreedModel>) {
        val allOrderDiff = AddOrderDiffUtil(list, newList)
        val result = DiffUtil.calculateDiff(allOrderDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }


    /**
     * Este método se llama cuando el RecyclerView necesita una nueva vista para representar un ítem.
     * Aquí es donde se infla el diseño de los ítems del RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCatBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size


    /**
     * Este método se llama para asignar datos a las vistas infladas.
     * Usa el ViewHolder proporcionado para configurar la vista del ítem en la posición específica.
     */

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, onItemSelected)
    }

    class MyViewHolder(private val binding: ItemCatBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BreedModel, onItemSelected: (BreedModel) -> Unit) {
            binding.apply {
                catBreedName.text = item.name
                catBreedMore.text = item.id
                catBreedCountry.text = item.origin
                catBreedImg.loadCatBreadImg(item.imageId)
                catBreedTemperament.text = item.temperament.substringBefore(",")
            }
        }

    }


}