package com.example.recipesearchapp.data.remote

import com.example.recipesearchapp.data.remote.dto.RandomRecipeResponse
import com.example.recipesearchapp.data.remote.dto.RecipeSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeSearchApi  {

    @GET("recipes/random")
    suspend fun getPopularRecipes(
        @Query("number") number: Int=10,
        @Query("apiKey") apikey: String = API_KEY
    ): RandomRecipeResponse

    @GET("recipes/complexSearch")
    suspend fun getAllRecipes(
        @Query("query") query: String,
        @Query("apiKey") apikey: String = API_KEY
    ): RecipeSearchResponseDto
    companion object{
        const val API_KEY = "8626b8c94f9e490291f7fcd1ccffa6bf"
        const val BASE_URL = "https://api.spoonacular.com/"
    }
}