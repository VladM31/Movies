package huge.of.movies.domain.models

data class MainState(
    val sortField: SortField = SortField.TITLE,
    val movies: List<Movie> = emptyList(),
    val pinnedMovies: Set<Long> = emptySet(),
)
