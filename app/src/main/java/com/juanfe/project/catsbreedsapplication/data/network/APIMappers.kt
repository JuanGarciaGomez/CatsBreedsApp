package com.juanfe.project.catsbreedsapplication.data.network

import com.juanfe.project.catsbreedsapplication.data.network.response.BreedFullResponse
import com.juanfe.project.catsbreedsapplication.data.network.response.BreedResponse
import com.juanfe.project.catsbreedsapplication.domain.BreedFullModel
import com.juanfe.project.catsbreedsapplication.domain.BreedModel

fun BreedResponse.toDomain() =
    BreedModel(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        imageId = imageId ?: "",
        lifeSpan = lifeSpan
    )


fun BreedFullResponse.toDomain() = BreedFullModel(
    id = id,
    name = name,
    origin = origin,
    description = description,
    temperament = temperament,
    imageId = imageId ?: "",
    lifeSpan = lifeSpan,
    affectionLevel = affectionLevel,
    adaptability = adaptability,
    intelligence = intelligence,
    energyLevel = energyLevel,
    socialNeeds = socialNeeds,
    strangerFriendly = strangerFriendly,
    dogFriendly = dogFriendly,
    childFriendly = childFriendly
)