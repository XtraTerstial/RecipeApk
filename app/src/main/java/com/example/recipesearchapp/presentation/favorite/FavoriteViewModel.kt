package com.example.recipesearchapp.presentation.favorite


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearchapp.domain.Repo.RecipeRepo
import com.example.recipesearchapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: RecipeRepo
): ViewModel() {

    var state by mutableStateOf(FavoriteScreenState())
        private set

    init {
        loadFavoriteRecipes()
    }

    fun onEvent(event: FavoriteScreenEvent) {
        when(event) {
            is FavoriteScreenEvent.LoadFavorites -> {
                loadFavoriteRecipes()
            }

            is FavoriteScreenEvent.Refresh -> {
                loadFavoriteRecipes()
            }

            is FavoriteScreenEvent.RemoveFromFavorites -> {
                removeFromFavorites(event.recipe)
            }

            is FavoriteScreenEvent.ToggleFavorite -> {
                toggleFavorite(event.recipe)
            }
        }
    }

    private fun loadFavoriteRecipes() {
        viewModelScope.launch {
            repository
                .getAllFavoriteRecipe()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { recipes ->
                                state = state.copy(
                                    favoriteRecipes = recipes,
                                    error = "",
                                    isEmpty = recipes.isEmpty()
                                )
                            }
                            Log.d("FavoriteScreen", "Favorite recipes loaded: ${state.favoriteRecipes.size}")
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                error = result.message ?: "Unknown error occurred",
                                isLoading = false
                            )
                            Log.e("FavoriteScreen", "Error loading favorite recipes: ${result.message}")
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

    private fun removeFromFavorites(recipe: com.example.recipesearchapp.domain.model.Recipe) {
        viewModelScope.launch {
            try {
                repository.deleteFavoriteRecipe(recipe.id)

                // Update local state immediately for better UX
                val updatedFavorites = state.favoriteRecipes.filter { it.id != recipe.id }
                state = state.copy(
                    favoriteRecipes = updatedFavorites,
                    isEmpty = updatedFavorites.isEmpty()
                )

                Log.d("FavoriteScreen", "Removed recipe from favorites: ${recipe.title}")
            } catch (e: Exception) {
                Log.e("FavoriteScreen", "Error removing from favorites: ${e.message}")
                state = state.copy(error = "Failed to remove from favorites")
            }
        }
    }

    private fun toggleFavorite(recipe: com.example.recipesearchapp.domain.model.Recipe) {
        viewModelScope.launch {
            try {
                if (recipe.isFavorite) {
                    repository.deleteFavoriteRecipe(recipe.id)
                } else {
                    repository.insertFavoriteRecipe(recipe)
                }

                // Reload favorites to get updated list
                loadFavoriteRecipes()

                Log.d("FavoriteScreen", "Toggled favorite for recipe: ${recipe.title}")
            } catch (e: Exception) {
                Log.e("FavoriteScreen", "Error toggling favorite: ${e.message}")
                state = state.copy(error = "Failed to update favorite")
            }
        }
    }
}
