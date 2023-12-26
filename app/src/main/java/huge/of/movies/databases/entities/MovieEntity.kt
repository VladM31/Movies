package huge.of.movies.databases.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import huge.of.movies.databases.converters.StringListConverter

@Entity(tableName = "movie")
@TypeConverters(StringListConverter::class)
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val description: String,
    val rating: Float,
    val genres: List<String>,
    val duration: String,
    val releasedDate: Long,
    val trailerLink: String,
    val image: String
)
