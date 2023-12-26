package huge.of.movies.domain.managers.watchlist

import huge.of.movies.databases.dao.WatchListDao
import huge.of.movies.databases.entities.WatchListEntity
import huge.of.movies.domain.models.WatchList

class WatchListManagerImpl(
    private val watchListDao: WatchListDao
) : WatchListManager {
    override suspend fun findAll(): List<WatchList> {
        return watchListDao.findAll().map { WatchList(it.movieId) }
    }

    override suspend fun findById(id: Long): WatchList? {
        return watchListDao.findAll(id)
            .firstOrNull()
            ?.let { WatchList(it.movieId) }
    }

    override suspend fun insert(watchList: WatchList) {
        watchListDao.insert(WatchListEntity(watchList.movieId))
    }

    override suspend fun deleteById(id: Long) {
        watchListDao.deleteById(id)
    }
}