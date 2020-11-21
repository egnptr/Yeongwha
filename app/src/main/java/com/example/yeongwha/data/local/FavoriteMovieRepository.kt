package com.example.yeongwha.data.local

import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(private val favoriteMovieDao: FavoriteMovieDao) {
    suspend fun addToFavorite(favoriteMovie: FavoriteMovie) = favoriteMovieDao.addToFavorite(favoriteMovie)
    fun getFavoriteMovies() = favoriteMovieDao.getFavoriteMovie()
    suspend fun checkMovie(id: Int) = favoriteMovieDao.checkMovie(id)
    suspend fun removeFromFavorite(id: Int) {
        favoriteMovieDao.removeFromFavorite(id)
    }
}