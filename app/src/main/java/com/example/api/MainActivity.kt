package com.example.api

import com.example.api.adapter.AnimeAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api.databinding.ActivityMainBinding
import com.example.api.model.AnimeListEntry
import com.example.api.model.GraphQLRequest
import com.example.api.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var animeAdapter: AnimeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView(emptyList())


        binding.searchButton.setOnClickListener {
            val userName = binding.usernameInput.text.toString()
            if (userName.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch{
                    val apiService = RetrofitInstance.api

                    val query = """
                        query GetUserAnimeList(${'$'}userName: String) {
                            MediaListCollection(userName: ${'$'}userName, type: ANIME, sort: SCORE_DESC) {
                                lists {
                                    entries {
                                        score
                                        media {
                                            title {
                                                english
                                            }
                                            coverImage {
                                                medium
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    """.trimIndent()

                    // Crear el objeto GraphQLRequest
                    val request = GraphQLRequest(
                        query = query,
                        variables = mapOf("userName" to userName)
                    )

                    try {
                        val response = apiService.getUserAnimeList(request)



                        val animeListEntries = response.data.MediaListCollection.lists.flatMap { it.entries }
                        if (animeListEntries.isNotEmpty()){
                            runOnUiThread {
                                animeAdapter.updateData(animeListEntries)
                            }
                        }


                    } catch (e: Exception){
                        Log.d("asd", "error", e)
                    }

                }
            } else {
                Toast.makeText(this, "Please enter a user name", Toast.LENGTH_SHORT).show()
            }

        }


    }
    private fun setupRecyclerView(lista: List<AnimeListEntry>) {
        animeAdapter = AnimeAdapter(lista)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.animeAdapter
        }
    }


}
