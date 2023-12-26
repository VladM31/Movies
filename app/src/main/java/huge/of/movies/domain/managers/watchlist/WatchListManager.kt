package huge.of.movies.domain.managers.watchlist

import huge.of.movies.domain.models.WatchList

interface WatchListManager {

    suspend fun findAll(): List<WatchList>

    suspend fun findById(id: Long): WatchList?

    suspend fun insert(watchList: WatchList)

    suspend fun deleteById(id: Long)
}