package com.example.recipesearchapp.presentation.home

import com.example.recipesearchapp.domain.model.Recipe

data class HomeScreenState(
    val isLoading: Boolean = false,
    val isPopularRecipesLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val popularRecipes: List<Recipe> = emptyList(),
    val error: String = "",
    val searchQuery: String = "",
    val isSearching: Boolean=false
)