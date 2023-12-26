package huge.of.movies.domain.managers.movie

import huge.of.movies.domain.models.Movie
import huge.of.movies.domain.models.SortField

interface MovieManager {
    suspend fun findAll(sortField: SortField): List<Movie>

    suspend fun findById(id: Long): Movie?
}