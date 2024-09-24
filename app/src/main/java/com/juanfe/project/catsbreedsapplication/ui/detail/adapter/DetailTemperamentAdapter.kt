package com.juanfe.project.catsbreedsapplication.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanfe.project.catsbreedsapplication.databinding.ItemCatBreedTemperamentBinding


class DetailTemperamentAdapter(
    private var list: List<String>,
) : RecyclerView.Adapter<DetailTemperamentAdapter.MyViewHolder>() {

    /**
     * Este método se llama cuando el RecyclerView necesita una nueva vista para representar un ítem.
     * Aquí es donde se infla el diseño de los ítems del RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCatBreedTemperamentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size


    /**
     * Este método se llama para asignar datos a las vistas infladas.
     * Usa el ViewHolder proporcionado para configurar la vista del ítem en la posición específica.
     */

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ItemCatBreedTemperamentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.apply {
                chip.text = item
            }
        }

    }


}