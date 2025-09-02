package com.example.recipesearchapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeEntity (
    @PrimaryKey
    val id: Long,
    val title: String,
    val image: String,
    val readyInMinutes: Long
)