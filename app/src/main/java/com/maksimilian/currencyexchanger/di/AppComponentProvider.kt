package com.maksimilian.currencyexchanger.di

import androidx.fragment.app.Fragment

interface AppComponentProvider {
    val appComponent: AppComponent
}

val Fragment.injector: AppComponent
    get() = (requireActivity().application as AppComponentProvider).appComponent