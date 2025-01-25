import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.api.R
import com.example.api.databinding.ItemAnimeBinding
import com.example.api.model.Anime
import com.example.api.model.AnimeListEntry
import com.squareup.picasso.Picasso


class AnimeAdapter : ListAdapter<AnimeListEntry, AnimeAdapter.AnimeViewHolder>(AnimeDiffCallback()) {

    private lateinit var binding: ItemAnimeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val animeEntry = getItem(position)  // Cambié el tipo a AnimeListEntry
        holder.bind(animeEntry)
    }

    inner class AnimeViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        fun bind(animeEntry: AnimeListEntry) {
            val anime = animeEntry.media
            binding.titleText.text = anime.title.userPreferred
            Picasso.get().load(anime.coverImage.large).into(binding.coverImage)
            binding.scoreText.text = animeEntry.score.toString()  // Accedes al score aquí
        }
    }

    class AnimeDiffCallback : DiffUtil.ItemCallback<AnimeListEntry>() {
        override fun areItemsTheSame(oldItem: AnimeListEntry, newItem: AnimeListEntry): Boolean {
            return oldItem.media.title.userPreferred == newItem.media.title.userPreferred  // Comparar por ID de anime
        }

        override fun areContentsTheSame(oldItem: AnimeListEntry, newItem: AnimeListEntry): Boolean {
            return oldItem == newItem  // Comparar si los contenidos son iguales
        }
    }
}
