package com.example.yeongwha.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yeongwha.R
import com.example.yeongwha.popular_movie.MovieActivity
import kotlinx.android.synthetic.main.activity_search.*

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        imageButtonBack.setOnClickListener {
            val intent = Intent(this, MovieActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}