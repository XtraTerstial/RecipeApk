package com.example.recipesearchapp.presentation.home

import com.example.recipesearchapp.domain.model.Recipe

sealed class HomeScreenEvent {
    object Refresh : HomeScreenEvent()
    object LoadPopularRecipes : HomeScreenEvent()
    data class OnSearchQuery(val query: String) : HomeScreenEvent()
    data class ToggleFavorite(val recipe: Recipe) : HomeScreenEvent()
    object ClearSearch : HomeScreenEvent()
}