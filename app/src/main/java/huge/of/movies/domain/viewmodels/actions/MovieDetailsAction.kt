package huge.of.movies.domain.viewmodels.actions

sealed interface MovieDetailsAction {
    data class LoadMovie(val id: Long) : MovieDetailsAction
    data object ToggleWatchList : MovieDetailsAction
}