package com.example.recipesearchapp.di

import com.example.recipesearchapp.data.repo.RecipeRepoImpl
import com.example.recipesearchapp.domain.Repo.RecipeRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRecipieRepository(
        recipieRepositoryImpl: RecipeRepoImpl
    ): RecipeRepo
}