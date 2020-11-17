package com.example.yeongwha.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yeongwha.R
import com.example.yeongwha.data.api.FIRST_PAGE
import com.example.yeongwha.data.api.TMDBClient
import com.example.yeongwha.data.api.TMDBInterface
import com.example.yeongwha.popular_movie.MainActivity
import com.example.yeongwha.popular_movie.MainActivityViewModel
import com.example.yeongwha.popular_movie.MoviePageListRepository
import com.example.yeongwha.popular_movie.PageListAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    lateinit var movieRepository: MoviePageListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        imageButtonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        SearchMovie()
    }

    private fun SearchMovie() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                ShowMovie(query)
                return false
            }

        })
    }

    private fun ShowMovie(query: String) {
        val movieAdapter = PageListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 3)
        viewModel = getViewModel()

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1
                else return 3
            }
        };

        rv_search.layoutManager = gridLayoutManager
        rv_search.setHasFixedSize(true)
        rv_search.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })
    }

    private fun getViewModel(): MainActivityViewModel {
        val apiService : TMDBInterface = TMDBClient.getClient()
        movieRepository = MoviePageListRepository(apiService)

        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}