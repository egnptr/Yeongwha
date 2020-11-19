package com.example.yeongwha.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.yeongwha.data.repository.NetworkState
import com.example.yeongwha.data.value_object.Movie
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository : MoviePageListRepository, private val query: String?) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  searchPagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.getSearchMovie(compositeDisposable, query)
    }

    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}