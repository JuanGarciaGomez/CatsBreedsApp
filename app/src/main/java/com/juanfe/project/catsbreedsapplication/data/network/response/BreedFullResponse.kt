package com.juanfe.project.catsbreedsapplication.data.network.response

import com.google.gson.annotations.SerializedName

data class BreedFullResponse(
    val id: String,
    val name: String,
    val origin: String,
    val description: String,
    val temperament: String,
    @SerializedName("reference_image_id")
    val imageId: String?,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("affection_level")
    val affectionLevel: Int,
    val adaptability: Int,
    val intelligence: Int,
    @SerializedName("energy_level")
    val energyLevel: Int,
    @SerializedName("social_needs")
    val socialNeeds: Int,
    @SerializedName("stranger_friendly")
    val strangerFriendly: Int,
    @SerializedName("dog_friendly")
    val dogFriendly: Int,
    @SerializedName("child_friendly")
    val childFriendly: Int,
)
