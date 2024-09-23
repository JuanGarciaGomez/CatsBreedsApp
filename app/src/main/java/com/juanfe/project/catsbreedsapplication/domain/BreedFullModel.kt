package com.juanfe.project.catsbreedsapplication.domain

data class BreedFullModel(
    val id: String,
    val name: String,
    val origin: String,
    val description: String,
    val temperament: String,
    val imageId: String,
    val lifeSpan: String,
    val affectionLevel: Int,
    val adaptability: Int,
    val intelligence: Int,
    val energyLevel: Int,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val dogFriendly: Int,
    val childFriendly: Int,
)
