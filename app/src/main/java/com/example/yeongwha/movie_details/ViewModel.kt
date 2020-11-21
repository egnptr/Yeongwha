package com.example.yeongwha.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.yeongwha.data.local.FavoriteMovie
import com.example.yeongwha.data.local.FavoriteMovieRepository
import com.example.yeongwha.data.repository.NetworkState
import com.example.yeongwha.data.value_object.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel (private val movieRepository : MovieDetailsRepository, movieId: Int)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


    fun addToFavorite(repository: FavoriteMovieRepository, movieDetails: MovieDetails){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteMovie(
                    movieDetails.id,
                    movieDetails.posterPath,
                    movieDetails.releaseDate,
                    movieDetails.title
                )
            )
        }
    }

    suspend fun checkMovie(repository: FavoriteMovieRepository, id: Int) = repository.checkMovie(id)

    fun removeFromFavorite(repository: FavoriteMovieRepository, id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromFavorite(id)
        }
    }

}