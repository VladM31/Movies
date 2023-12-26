package huge.of.movies.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import huge.of.movies.databases.dao.MovieDao
import huge.of.movies.databases.dao.WatchListDao
import huge.of.movies.databases.entities.MovieEntity
import huge.of.movies.databases.entities.WatchListEntity

@Database(
    entities = [
        MovieEntity::class,
        WatchListEntity::class
    ], version = 1
)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    abstract fun getWatchListDao(): WatchListDao
}