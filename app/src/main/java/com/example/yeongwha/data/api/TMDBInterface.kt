package com.example.yeongwha.data.api

import com.example.yeongwha.data.value_object.MovieDetails
import com.example.yeongwha.data.value_object.MovieResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBInterface {
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Observable<MovieResponse>
}