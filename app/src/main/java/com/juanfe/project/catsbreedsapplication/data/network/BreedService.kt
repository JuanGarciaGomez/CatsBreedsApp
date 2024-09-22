package com.juanfe.project.catsbreedsapplication.data.network

import com.juanfe.project.catsbreedsapplication.data.network.response.BreedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedService {
    @GET("breeds")
    suspend fun getCatBreedList(
        @Query("limit") limit: Int = 15,
        @Query("page") page: Int = 0,
    ): Response<List<BreedResponse>>
    
    @GET("breeds/search")
    suspend fun searchBreed(
        @Query("q") query: String,
        @Query("attach_image") attachImage: Int = 1,
    ): Response<List<BreedResponse>>

}
