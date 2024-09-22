package com.juanfe.project.catsbreedsapplication.data.network

import com.juanfe.project.catsbreedsapplication.data.network.response.BreedResponse
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