package com.example.yeongwha.movie_details

import androidx.lifecycle.LiveData
import com.example.yeongwha.data.api.TMDBInterface
import com.example.yeongwha.data.local.FavoriteMovie
import com.example.yeongwha.data.local.FavoriteMovieDao
import com.example.yeongwha.data.repository.DataSources
import com.example.yeongwha.data.repository.NetworkState
import com.example.yeongwha.data.value_object.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService : TMDBInterface) {

    lateinit var movieDetailsNetworkDataSource: DataSources
    lateinit var favoriteMovieDao: FavoriteMovieDao

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = DataSources(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }

    suspend fun addToFavorite(favoriteMovie: FavoriteMovie) = favoriteMovieDao.addToFavorite(favoriteMovie)
    fun getFavoriteMovies() = favoriteMovieDao.getFavoriteMovie()
    suspend fun checkMovie(id: Int) = favoriteMovieDao.checkMovie(id)
    suspend fun removeFromFavorite(id: Int) {
        favoriteMovieDao.removeFromFavorite(id)
    }
}