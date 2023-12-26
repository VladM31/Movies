package huge.of.movies.ui.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import huge.of.movies.R
import huge.of.movies.databinding.ActivityMainBinding
import huge.of.movies.databinding.DialogSortBinding
import huge.of.movies.domain.viewmodels.MainViewModel
import huge.of.movies.domain.viewmodels.actions.MainActions
import huge.of.movies.ui.adapters.MovieAdapter
import huge.of.movies.ui.decorations.LineDecoration
import huge.of.movies.ui.dialogs.createSortDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by viewModel()
    private val detailsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode != RESULT_OK) {
           return@registerForActivityResult
        }
        result.data?.getLongExtra(MovieDetailsActivity.MOVIE_ID, MovieDetailsActivity.DEF_MOVIE_ID)?.let { movieId ->
            if (MovieDetailsActivity.DEF_MOVIE_ID != movieId) {
                mainViewModel.sentAction(MainActions.TogglePinnedMovie(movieId))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.moviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(LineDecoration(this@MainActivity))
        }

        lifecycleScope.launch {
            mainViewModel.state.map { it.movies }.distinctUntilChanged().collectLatest {
                binding.moviesRecyclerView.adapter = MovieAdapter(
                    movies = it,
                    isPinned = { movie -> mainViewModel.state.value.pinnedMovies.contains(movie.id) },
                ) { movie ->
                    detailsLauncher.launch(MovieDetailsActivity.createIntent(this@MainActivity, movie.id))
                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.state.map { it.pinnedMovies }.distinctUntilChanged().collectLatest {
                binding.moviesRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

        binding.sortText.setOnClickListener {
            createSortDialog(
                activity = this,
                parent = binding.root,
                getSelectedSortField = { mainViewModel.state.value.sortField },
                selectSortField = { sortField ->
                    mainViewModel.sentAction(MainActions.SortBy(sortField))
                }
            )
        }
    }

    companion object{

    }
}