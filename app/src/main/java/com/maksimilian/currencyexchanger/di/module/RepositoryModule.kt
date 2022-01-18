package com.maksimilian.currencyexchanger.di.module

import com.maksimilian.currencyexchanger.data.repository.CurrencyRepositoryImpl
import com.maksimilian.currencyexchanger.domain.CurrencyRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindCurrencyRepository(impl: CurrencyRepositoryImpl): CurrencyRepository
}