package com.example.recipesearchapp.domain.model

data class Recipe(
    val id: Long,
    val title: String,
    val image: String,
    val readyInMinutes: Long = 25,
    val isFavorite: Boolean = false
)
