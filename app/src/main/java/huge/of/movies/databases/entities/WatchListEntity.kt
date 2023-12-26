package huge.of.movies.databases.entities

import androidx.room.Entity

@Entity(tableName = "watch_list", primaryKeys = ["movieId"])
data class WatchListEntity(
    val movieId: Long,
)
