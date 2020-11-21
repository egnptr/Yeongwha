package com.example.yeongwha.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideFavMovieDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FavoriteMovieDatabase::class.java,
        "movie_db"
    ).build()

    @Singleton
    @Provides
    fun provideFavMovieDao(db: FavoriteMovieDatabase) = db.getFavoriteMovieDao()

}