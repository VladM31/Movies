package huge.of.movies.domain.models

import androidx.annotation.DrawableRes
import java.util.Date

data class Movie(
    val id: Long,
    val title: String,
    val description: String,
    val rating: Float,
    val genres: List<String>,
    val duration: String,
    val releasedDate: Date,
    val trailerLink: String,
    @DrawableRes
    val image: Int
)