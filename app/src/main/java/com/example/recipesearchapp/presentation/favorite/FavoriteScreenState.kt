package com.example.recipesearchapp.presentation.favorite

import com.example.recipesearchapp.domain.model.Recipe

data class FavoriteScreenState(
    val isLoading: Boolean = false,
    val favoriteRecipes: List<Recipe> = emptyList(),
    val error: String = "",
    val isEmpty: Boolean = false
)