package com.example.recipesearchapp.domain.Repo


import com.example.recipesearchapp.domain.model.Recipe
import com.example.recipesearchapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepo {
    suspend fun getAllRecipe(
        query: String
    ): Flow<Resource<List<Recipe>>>

    suspend fun getPopularRecipe(): Flow<Resource<List<Recipe>>>

    suspend fun getAllFavoriteRecipe(): Flow<Resource<List<Recipe>>>

    suspend fun deleteFavoriteRecipe(id:Long)

    suspend fun insertFavoriteRecipe(recipe: Recipe)
}