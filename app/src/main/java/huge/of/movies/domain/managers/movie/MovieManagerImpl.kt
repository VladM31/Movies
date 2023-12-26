package huge.of.movies.domain.managers.movie

import huge.of.movies.databases.dao.MovieDao
import huge.of.movies.domain.mappers.MovieMapper
import huge.of.movies.domain.models.Movie
import huge.of.movies.domain.models.SortField

class MovieManagerImpl(
    private val movieDao: MovieDao
): MovieManager {
    private val movieMapper = MovieMapper()
    private val sortFieldMap = mapOf(
        SortField.TITLE to "title",
        SortField.RELEASED_DATE to "releasedDate"
    )
    private val defSortField = "title"

    override suspend fun findAll(sortField: SortField): List<Movie> {
        val sortFields = movieDao.findAll(sortFieldMap.getOrDefault(sortField,defSortField))
        println(sortFields)

        return movieDao.findAll(sortFieldMap.getOrDefault(sortField,defSortField))
            .map { movieMapper.toMovie(it) }
    }

    override suspend fun findById(id: Long): Movie? {
        return movieDao.findById(id)?.let { movieMapper.toMovie(it) }
    }
}