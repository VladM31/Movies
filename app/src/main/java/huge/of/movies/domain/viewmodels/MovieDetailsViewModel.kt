package huge.of.movies.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import huge.of.movies.domain.managers.movie.MovieManager
import huge.of.movies.domain.managers.watchlist.WatchListManager
import huge.of.movies.domain.models.MovieDetailsState
import huge.of.movies.domain.models.WatchList
import huge.of.movies.domain.viewmodels.actions.MovieDetailsAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieManager: MovieManager,
    private val watchListManager: WatchListManager
) : ViewModel() {

    private val mutableState = MutableStateFlow(MovieDetailsState())
    val state = mutableState.asStateFlow()

    private var inWatchListFirstValue = false

    fun sentAction(action: MovieDetailsAction) {
        when (action) {
            is MovieDetailsAction.LoadMovie -> loadMovie(action.id)
            is MovieDetailsAction.ToggleWatchList -> handleChangeWatchList()
        }
    }

    private fun handleChangeWatchList() {
        viewModelScope.launch(Dispatchers.IO) {
            val movieId = mutableState.value.movie?.id ?: return@launch
            if (mutableState.value.inWatchList) {
                watchListManager.deleteById(movieId)
            } else {
                watchListManager.insert(WatchList(movieId))
            }

            val newIsWatchList = mutableState.value.inWatchList.not()

            mutableState.value = mutableState.value.copy(
                inWatchList = newIsWatchList,
                isChangedWatchList = inWatchListFirstValue != newIsWatchList
            )
        }
    }


    private fun loadMovie(id: Long) {
        mutableState.value = mutableState.value.copy(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            val inWatchList = watchListManager.findById(id) != null
            inWatchListFirstValue = inWatchList

            movieManager.findById(id)?.let { movie ->
                mutableState.value = mutableState.value.copy(
                    movie = movie,
                    isLoading = false,
                    inWatchList = inWatchList
                )
            }
        }
    }
}