package com.example.api

import AnimeAdapter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api.databinding.ActivityMainBinding
import com.example.api.model.UserAnimeListResponse
import com.example.api.model.UserIdResponse
import com.example.api.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var animeAdapter: AnimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animeAdapter = AnimeAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = animeAdapter

        binding.searchButton.setOnClickListener {
            val userName = binding.usernameInput.text.toString()
            if (userName.isNotEmpty()) {
                getUserId(userName)
            } else {
                Toast.makeText(this, "Please enter a user name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserId(userName: String) {
        // Usamos RetrofitInstance para obtener el servicio
        val apiService = RetrofitInstance.api
        val call = apiService.getUserId(userName)

        call.enqueue(object : Callback<UserIdResponse> {
            @RequiresApi(Build.VERSION_CODES.P)
            override fun onResponse(call: Call<UserIdResponse>, response: Response<UserIdResponse>) {
                if (response.isSuccessful) {
                    val userId = response.body()?.data?.id
                    userId?.let {
                        getUserAnimeList(userName, it.toInt())
                    }
                } else {
                    Toast.makeText(this@MainActivity, "User not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserIdResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUserAnimeList(userName: String, userId: Int) {
        // Usamos RetrofitInstance para obtener el servicio
        val apiService = RetrofitInstance.api
        val call = apiService.getUserAnimeList(userName, userId)

        call.enqueue(object : Callback<UserAnimeListResponse> {
            override fun onResponse(call: Call<UserAnimeListResponse>, response: Response<UserAnimeListResponse>) {
                if (response.isSuccessful) {
                    val animeList = response.body()?.data?.MediaListCollection?.lists?.flatMap { it.entries } ?: emptyList()
                    animeAdapter.submitList(animeList)
                } else {
                    Toast.makeText(this@MainActivity, "Error loading anime list", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserAnimeListResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
