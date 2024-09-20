package com.juanfe.project.catsbreedsapplication.data.network

import com.juanfe.project.catsbreedsapplication.data.network.response.BreedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedService {
    @GET("breeds")
    suspend fun getBodyPartList(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 0,
    ): Response<List<BreedResponse>>

}
