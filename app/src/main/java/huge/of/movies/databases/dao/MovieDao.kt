package huge.of.movies.databases.dao

import androidx.room.Dao
import androidx.room.Query
import huge.of.movies.databases.entities.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY  CASE WHEN :sortColumn = 'title' THEN title ELSE releasedDate END DESC")
    suspend fun findAll(sortColumn: String): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun findById(id: Long): MovieEntity?
}