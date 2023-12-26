package huge.of.movies.domain.mappers

import huge.of.movies.R
import huge.of.movies.databases.entities.MovieEntity
import huge.of.movies.domain.models.Movie
import java.util.Date

class MovieMapper {
    private val imageMap = mapOf(
        "avengers" to R.drawable.avengers,
        "guardians_of_the_galaxy" to R.drawable.guardians_of_the_galaxy,
        "knives_out" to R.drawable.knives_out,
        "spider_man" to R.drawable.spider_man,
        "tenet" to R.drawable.tenet,
    )

    fun toMovie(entity: MovieEntity): Movie {
        return Movie(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            rating = entity.rating,
            genres = entity.genres,
            duration = entity.duration,
            releasedDate = Date(entity.releasedDate),
            trailerLink = entity.trailerLink,
            image = imageMap[entity.image] ?: 0
        )
    }
}