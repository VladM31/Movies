package huge.of.movies.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import huge.of.movies.R

import huge.of.movies.databinding.ActivityMovieDetailsBinding
import huge.of.movies.domain.viewmodels.MovieDetailsViewModel
import huge.of.movies.domain.viewmodels.actions.MovieDetailsAction
import huge.of.movies.ui.callbacks.MovieDetailsBackCallback
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class MovieDetailsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }
    private val detailsViewModel: MovieDetailsViewModel by viewModel()

    @SuppressLint("SimpleDateFormat")
    private val releasedDateFormatter = SimpleDateFormat("yyyy, d MMMM")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindMovie()
        bindInWatchList()
        bindStartYoutube()

        binding.pinWatchList.setOnClickListener {
            detailsViewModel.sentAction(MovieDetailsAction.ToggleWatchList)
        }

        binding.backButton.setOnClickListener { handleEnd() }

        loadMovie()

        onBackPressedDispatcher.addCallback(this, MovieDetailsBackCallback {
            handleEnd()
        })
    }

    private fun handleEnd() {
        setResult(
            if (detailsViewModel.state.value.isChangedWatchList) RESULT_OK else RESULT_CANCELED,
            Intent().apply {
                putExtra(MOVIE_ID, detailsViewModel.state.value.movie?.id)
            })
        finish()
    }

    private fun bindMovie() {
        lifecycleScope.launch {
            detailsViewModel.state.map { it.movie }.filterNotNull().take(1).collect { movie ->
                binding.apply {
                    titleText.text = movie.title
                    descriptionText.text = movie.description
                    ratingText.text = movie.rating.toString()
                    releasedDateText.text = releasedDateFormatter.format(movie.releasedDate)
                    poster.setImageResource(movie.image)
                }
            }
        }
    }

    private fun bindStartYoutube() {
        binding.openTrailer.setOnClickListener {
            val trailerLink =
                detailsViewModel.state.value.movie?.trailerLink ?: return@setOnClickListener

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        trailerLink.toUri()
                    )
                )
            } catch (_: Exception) {
                Toast.makeText(
                    this,
                    R.string.can_t_open_trailer,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun bindInWatchList() {
        lifecycleScope.launch {
            detailsViewModel.state.map { it.inWatchList }.distinctUntilChanged()
                .collect { inWatchList ->
                    binding.pinWatchList.text = getString(
                        if (inWatchList) {
                            R.string.remove_from_watchlist
                        } else {
                            R.string.add_to_watchlist
                        }
                    )
                }
        }
    }

    private fun loadMovie() {
        val id = intent.getLongExtra(MOVIE_ID, DEF_MOVIE_ID)
        if (detailsViewModel.state.value.run { isLoading.not() && movie?.id != id }) {

            if (id == DEF_MOVIE_ID) {
                finish()
                setResult(RESULT_CANCELED)
                return
            }
            detailsViewModel.sentAction(
                MovieDetailsAction.LoadMovie(
                    intent.getLongExtra(
                        MOVIE_ID,
                        id
                    )
                )
            )
        }
    }

    companion object {
        const val MOVIE_ID = "movieId"
        const val DEF_MOVIE_ID = -1L

        fun createIntent(context: Context, movieId: Long): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }
}