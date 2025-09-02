package com.example.recipesearchapp.data.repo

import coil.network.HttpException
import com.example.recipesearchapp.data.local.RecipeDb
import com.example.recipesearchapp.data.mapper.toRecipe
import com.example.recipesearchapp.data.mapper.toRecipeEntity
import com.example.recipesearchapp.data.remote.RecipeSearchApi
import com.example.recipesearchapp.data.remote.dto.RecipeDto
import com.example.recipesearchapp.domain.Repo.RecipeRepo
import com.example.recipesearchapp.domain.model.Recipe
import com.example.recipesearchapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepoImpl @Inject constructor(
    val api: RecipeSearchApi,
    val db: RecipeDb
): RecipeRepo {
    private val dao = db.dao
    fun getRecipes() : Flow<Resource<List<RecipeDto>>> {
        return flow{
            emit(Resource.Loading(true))

        }
    }

    override suspend fun getAllRecipe(query: String): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val remoteRecipes = api.getAllRecipes(query).results // Change here
                val favouriteIds = dao.getAllFavoriteRecipesId()

                val recipes = remoteRecipes.map { dto ->
                    dto.toRecipe().copy(
                        isFavorite = favouriteIds.contains(dto.id)
                    )
                }

                emit(Resource.Success(recipes))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check internet."))
            } catch (e: HttpException) {
                emit(Resource.Error("Server error: ${e.response.code}"))
            } finally {
                emit(Resource.Loading(false))
                }
            }
        }

    override suspend fun getPopularRecipe(): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading(true))

            val apiResponse = try {
                api.getPopularRecipes().recipes // Change here
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data: ${e.localizedMessage ?: "Unknown error"}"))
                null
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
                null
            }

            apiResponse?.let {
                emit(Resource.Success(it.map { it -> it.toRecipe() }))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getAllFavoriteRecipe(): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading(true))
            val apiResponse = try {
                dao.getAllFavoriteRecipes()
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            if(apiResponse!=null){
                emit(Resource.Success(
                    data = apiResponse.map { it.toRecipe() }
                ))
                emit(Resource.Loading(false))
                return@flow
            }else{
                emit(Resource.Error( "Couldn't load data"))
                emit(Resource.Loading(false))

            }
        }
    }

    override suspend fun deleteFavoriteRecipe(id: Long) {
        try{
            dao.deleteFavoriteRecipes(id = id)
        }catch(e: Exception){
            e.printStackTrace()
        }
    }

    override suspend fun insertFavoriteRecipe(recipe: Recipe) {
        try{
            dao.insertFavoriteRecipes(favoriteRecipeEntity = recipe.toRecipeEntity())
        }catch(e: Exception){
            e.printStackTrace()
        }
    }


}