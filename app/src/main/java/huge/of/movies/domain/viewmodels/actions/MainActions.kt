package huge.of.movies.domain.viewmodels.actions

import huge.of.movies.domain.models.SortField

sealed interface MainActions {
    data class SortBy(val sortField: SortField) : MainActions
    data class TogglePinnedMovie(val movieId: Long) : MainActions
}