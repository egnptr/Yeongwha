package com.example.yeongwha.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.yeongwha.data.api.TMDBInterface
import com.example.yeongwha.data.value_object.Movie
import io.reactivex.disposables.CompositeDisposable

class DataSourceFactory (private val apiService : TMDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}