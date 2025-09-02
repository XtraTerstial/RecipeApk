package com.example.recipesearchapp.data.mapper

import com.example.recipesearchapp.data.local.RecipeEntity
import com.example.recipesearchapp.data.remote.dto.RecipeDto
import com.example.recipesearchapp.domain.model.Recipe

fun RecipeDto.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(id = id, title = title, image = image, readyInMinutes = readyInMinutes)
}

fun RecipeEntity.toRecipeDto(): RecipeDto {
    return RecipeDto(id = id, title = title, image = image, readyInMinutes = readyInMinutes)
}
fun RecipeDto.toRecipe(): Recipe {
    return Recipe(id = id, title = title, image = image, readyInMinutes = readyInMinutes)
}

fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(id = id, title = title, image = image, readyInMinutes = readyInMinutes)

}

fun Recipe.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(id = id, title = title, image = image, readyInMinutes = readyInMinutes)

}