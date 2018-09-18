
package com.kotlin.test.core.di

import com.kotlin.test.AndroidApplication
import com.kotlin.test.core.di.viewmodel.ViewModelModule
import com.kotlin.test.core.navigation.RouteActivity
import com.kotlin.test.presentation.details.MovieDetailsFragment
import com.kotlin.test.presentation.movies.MoviesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)

    fun inject(moviesFragment: MoviesFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
}
