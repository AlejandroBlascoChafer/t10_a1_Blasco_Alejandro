package com.example.api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.api.databinding.ItemAnimeBinding
import com.example.api.model.AnimeListEntry
import com.squareup.picasso.Picasso
import com.example.api.R


class AnimeAdapter(private var listaAnime: List<AnimeListEntry>):
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {


    private lateinit var context: Context

    inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemAnimeBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)


    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val animeEntry = listaAnime.get(position)
        with (holder){
            binding.scoreText.text = animeEntry.score.toString()
            binding.titleText.text = animeEntry.media.title.english
            val imagen: String = animeEntry.media.coverImage.medium
            Picasso.get().load(imagen).into(binding.coverImage)
        }
    }

    override fun getItemCount(): Int = listaAnime.size

    fun updateData(newPeliculas: List<AnimeListEntry>) {
        listaAnime = newPeliculas
        notifyDataSetChanged()
    }

}
