package huge.of.movies.databases.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import huge.of.movies.databases.entities.WatchListEntity

@Dao
interface WatchListDao {

    @Query("SELECT * FROM watch_list WHERE (:id IS NULL OR movieId = :id)")
    suspend fun findAll(id: Long? = null): List<WatchListEntity>

    @Insert
    suspend fun insert(watchList: WatchListEntity)

    @Query("DELETE FROM watch_list WHERE movieId = :id")
    suspend fun deleteById(id: Long)
}