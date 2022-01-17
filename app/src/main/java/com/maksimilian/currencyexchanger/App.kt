package com.maksimilian.currencyexchanger

import android.app.Application
import com.maksimilian.currencyexchanger.data.network.core.ServerSettings
import com.maksimilian.currencyexchanger.di.AppComponent
import com.maksimilian.currencyexchanger.di.AppComponentProvider
import com.maksimilian.currencyexchanger.di.DaggerAppComponent
import com.maksimilian.currencyexchanger.di.module.NetworkModule

class App : Application(), AppComponentProvider {

    private val serverSettings = ServerSettings(BuildConfig.SERVER_URL, BuildConfig.ACCESS_KEY)

    override val appComponent: AppComponent = DaggerAppComponent.builder()
        .bindApplication(this)
        .networkModule(NetworkModule(serverSettings))
        .build()
}