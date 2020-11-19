package com.example.yeongwha.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.yeongwha.data.api.POST_PER_PAGE
import com.example.yeongwha.data.api.TMDBInterface
import com.example.yeongwha.data.repository.DataSourceFactory
import com.example.yeongwha.data.repository.MovieDataSource
import com.example.yeongwha.data.repository.NetworkState
import com.example.yeongwha.data.value_object.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository (private val apiService: TMDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: DataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = DataSourceFactory(apiService, null, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getSearchMovie (compositeDisposable: CompositeDisposable, query : String?) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = DataSourceFactory(apiService, query, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}