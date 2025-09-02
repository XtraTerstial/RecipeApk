package com.example.recipesearchapp.presentation.favorite

import com.example.recipesearchapp.domain.model.Recipe

sealed class FavoriteScreenEvent {
    object LoadFavorites : FavoriteScreenEvent()
    object Refresh : FavoriteScreenEvent()
    data class RemoveFromFavorites(val recipe: Recipe) : FavoriteScreenEvent()
    data class ToggleFavorite(val recipe: Recipe) : FavoriteScreenEvent()
}