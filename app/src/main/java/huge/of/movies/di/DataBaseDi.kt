package huge.of.movies.di

import androidx.room.Room
import huge.of.movies.databases.MoviesDataBase
import huge.of.movies.databases.converters.StringListConverter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataBaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), MoviesDataBase::class.java,"movies")
            .addTypeConverter(
                StringListConverter()
            )
            .createFromAsset("init.db")
            .build()
    }

    single {
        get<MoviesDataBase>().getMovieDao()
    }

    single {
        get<MoviesDataBase>().getWatchListDao()
    }
}