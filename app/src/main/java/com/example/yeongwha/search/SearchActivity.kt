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
import com.example.yeongwha.data.api.TMDBClient
import com.example.yeongwha.data.api.TMDBInterface
import com.example.yeongwha.popular_movie.MovieActivity
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
            val intent = Intent(this, MovieActivity::class.java)
            startActivity(intent)
            finish()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                showMovie(query)
                return false
            }
        })
    }

    private fun showMovie(query: String) {
        val apiService : TMDBInterface = TMDBClient.getClient()
        movieRepository = MoviePageListRepository(apiService)

        viewModel = getViewModel(query)

        val movieAdapter = PageListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        };

        rv_search.layoutManager = gridLayoutManager
        rv_search.setHasFixedSize(true)
        rv_search.adapter = movieAdapter

        viewModel.searchPagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })
    }

    private fun getViewModel(query: String): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository,query) as T
            }
        })[MainActivityViewModel::class.java]
    }
}