package com.juanfe.project.catsbreedsapplication.data.repository.di

import com.juanfe.project.catsbreedsapplication.data.repository.CatBreedRepository
import com.juanfe.project.catsbreedsapplication.data.repository.CatBreedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsCatBreedRepository(catBreedRepositoryImpl: CatBreedRepositoryImpl): CatBreedRepository


}