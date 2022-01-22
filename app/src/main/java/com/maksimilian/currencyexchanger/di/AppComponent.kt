package com.maksimilian.currencyexchanger.di

import android.app.Application
import com.maksimilian.currencyexchanger.di.module.*
import com.maksimilian.currencyexchanger.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        DataSourceModule::class,
        UseCaseModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplication(application: Application): Builder

        fun networkModule(module: NetworkModule): Builder
        fun build(): AppComponent
    }
}