package com.juanfe.project.catsbreedsapplication.ui.landing.adapter

import androidx.recyclerview.widget.DiffUtil
import com.juanfe.project.catsbreedsapplication.data.network.response.BreedResponse
import com.juanfe.project.catsbreedsapplication.domain.BreedModel


class AddOrderDiffUtil(private val oldList: List<BreedModel>, private val newList: List<BreedModel>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // I can use whatever validations that i need
        return  oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return  oldList[oldItemPosition] == newList[newItemPosition]
    }


}