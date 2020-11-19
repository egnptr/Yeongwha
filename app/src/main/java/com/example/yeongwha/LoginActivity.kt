package com.example.yeongwha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.yeongwha.popular_movie.MovieActivity

class LoginActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var  etPassword: EditText
    val MIN_PASSWORD_LENGTH = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewInitializations()
    }

    fun viewInitializations() {
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun validateInput(): Boolean {
        if (etEmail.text.toString() == "") {
            etEmail.error = "Please Enter Email"
            return false
        }
        if (etPassword.text.toString() == "") {
            etPassword.error = "Please Enter Password"
            return false
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.text.toString())) {
            etEmail.error = "Please Enter Valid Email"
            return false
        }

        // checking minimum password Length
        if (etPassword.text.length < MIN_PASSWORD_LENGTH) {
            etPassword.error = "Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters"
            return false
        }
        return true
    }

    fun isEmailValid(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun performSignUp(v: View) {
        if (validateInput()) {

            val email = etEmail!!.text.toString()
            val password = etPassword!!.text.toString()
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MovieActivity::class.java)
            startActivity(intent)
        }
    }
}