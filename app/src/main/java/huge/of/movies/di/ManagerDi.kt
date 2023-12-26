package huge.of.movies.di

import huge.of.movies.domain.managers.movie.MovieManager
import huge.of.movies.domain.managers.movie.MovieManagerImpl
import huge.of.movies.domain.managers.watchlist.WatchListManager
import huge.of.movies.domain.managers.watchlist.WatchListManagerImpl
import org.koin.dsl.module

val managerModule = module {
    single<MovieManager> { MovieManagerImpl(movieDao = get()) }

    single<WatchListManager> { WatchListManagerImpl(watchListDao = get()) }
}