package com.maksimilian.currencyexchanger.di

import android.app.Activity

interface AppComponentProvider {
    val appComponent: AppComponent
}

val Activity.injector: AppComponent get() = (application as AppComponentProvider).appComponent