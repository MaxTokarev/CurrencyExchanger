package com.maksimilian.currencyexchanger.di

import android.app.Application
import com.maksimilian.currencyexchanger.di.module.*
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        DataSourceModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplication(application: Application): Builder

        fun networkModule(module: NetworkModule): Builder
        fun build(): AppComponent
    }
}