package huge.of.movies.domain.models

data class MovieDetailsState(
    val movie: Movie? = null,
    val inWatchList: Boolean = false,
    val isChangedWatchList: Boolean = false,
    val isLoading: Boolean = false,
)
