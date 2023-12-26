package huge.of.movies

import android.app.Application
import huge.of.movies.di.dataBaseModule
import huge.of.movies.di.managerModule
import huge.of.movies.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(dataBaseModule,managerModule,viewModelModule)
        }
    }


}
