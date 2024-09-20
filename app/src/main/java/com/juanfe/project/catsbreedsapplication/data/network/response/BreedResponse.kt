package com.juanfe.project.catsbreedsapplication.data.network.response

import com.google.gson.annotations.SerializedName

data class BreedResponse(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    @SerializedName("reference_image_id")
    val imageId: String
)
