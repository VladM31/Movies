package huge.of.movies.di

import huge.of.movies.domain.viewmodels.MainViewModel
import huge.of.movies.domain.viewmodels.MovieDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            movieManager = get(),
            watchListManager = get()
        )
    }

    viewModel {
        MovieDetailsViewModel(
            movieManager = get(),
            watchListManager = get()
        )
    }
}