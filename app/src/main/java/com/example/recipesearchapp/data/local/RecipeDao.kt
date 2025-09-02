package com.example.recipesearchapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDao {
    @Insert
    suspend fun insertFavoriteRecipes(
        favoriteRecipeEntity: RecipeEntity
    )

    @Query("DELETE FROM RecipeEntity WHERE id = :id")
    suspend fun deleteFavoriteRecipes(id: Long)

    @Query("SELECT * FROM recipeentity")
    suspend fun getAllFavoriteRecipes(): List<RecipeEntity>

    @Query("SELECT id FROM recipeentity")
    suspend fun getAllFavoriteRecipesId(): List<Long>
}