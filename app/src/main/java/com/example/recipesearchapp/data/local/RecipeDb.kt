package com.example.recipesearchapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDb : RoomDatabase() {
    abstract val dao: RecipeDao
}