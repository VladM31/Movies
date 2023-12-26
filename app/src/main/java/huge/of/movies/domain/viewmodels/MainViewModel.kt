package huge.of.movies.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import huge.of.movies.domain.managers.movie.MovieManager
import huge.of.movies.domain.managers.watchlist.WatchListManager
import huge.of.movies.domain.models.MainState
import huge.of.movies.domain.models.SortField
import huge.of.movies.domain.viewmodels.actions.MainActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val movieManager: MovieManager,
    private val watchListManager: WatchListManager
) : ViewModel() {


    private val mutableState = MutableStateFlow(MainState())
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            mutableState.value = mutableState.value.copy(
                movies = movieManager.findAll(state.value.sortField),
                pinnedMovies = watchListManager.findAll().map { it.movieId }.toSet()
            )
        }
    }


    fun sentAction(action: MainActions) {
        when (action) {
            is MainActions.SortBy -> handleSortBy(action.sortField)
            is MainActions.TogglePinnedMovie -> handleTogglePinnedMovie(action.movieId)
        }
    }

    private fun handleTogglePinnedMovie(movieId: Long) {
        mutableState.value = mutableState.value.copy(
            pinnedMovies = mutableState.value.pinnedMovies.let {
                if (mutableState.value.pinnedMovies.contains(movieId)){
                    it.minus(movieId)
                }else{
                    it.plus(movieId)
                }
            }
        )
    }

    private fun handleSortBy(sortField: SortField) {
        if (mutableState.value.sortField == sortField) {
            return
        }

        viewModelScope.launch(Dispatchers.IO){
            val movies = movieManager.findAll(sortField)
            mutableState.value = mutableState.value.copy(
                sortField = sortField,
                movies = movies
            )
        }
    }
}