package huge.of.movies.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import huge.of.movies.databinding.BoxMovieBinding
import huge.of.movies.domain.models.Movie
import java.util.Calendar

class MovieAdapter(
    private val movies: List<Movie>,
    private val isPinned: (Movie) -> Boolean,
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    class MovieViewHolder(
        private val binding: BoxMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        private val calendar = Calendar.getInstance()

        @SuppressLint("SetTextI18n")
        fun onBind(movie: Movie, isPinned: Boolean) {
            binding.poster.setImageResource(movie.image)
            binding.titleText.text = calendar.let {
                it.time = movie.releasedDate
                "${movie.title} (${it.get(Calendar.YEAR)})"
            }
            binding.timeGenresText.text = "${movie.duration} -  ${movie.genres.joinToString(", ")}"

            binding.isWatchlistText.visibility = if(isPinned) {
                android.view.View.VISIBLE
            } else {
                android.view.View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = BoxMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movies[position], isPinned(movies[position]))
        holder.itemView.setOnClickListener {
            onMovieClick(movies[position])
        }
    }
}