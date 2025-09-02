package com.example.recipesearchapp.data.remote.dto

data class RecipeDto(
    val id: Long,
    val title: String,
    val image: String,
    val readyInMinutes: Long
)
