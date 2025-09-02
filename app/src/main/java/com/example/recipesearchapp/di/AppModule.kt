package com.example.recipesearchapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import android.app.Application
import androidx.room.Room
import com.example.recipesearchapp.data.local.RecipeDb
import com.example.recipesearchapp.data.remote.RecipeSearchApi
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipieSearchApi(): RecipeSearchApi {
        return Retrofit.Builder()
            .baseUrl(RecipeSearchApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build()
            .create(RecipeSearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipieDatabase(app: Application): RecipeDb {
        return Room.databaseBuilder(
            app,
            RecipeDb::class.java,
            "stockdb.db"
        ).build()
    }

}