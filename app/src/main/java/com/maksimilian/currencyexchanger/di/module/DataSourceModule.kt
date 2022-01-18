package com.maksimilian.currencyexchanger.di.module

import com.maksimilian.currencyexchanger.data.local.source.CurrencyLocalDataSource
import com.maksimilian.currencyexchanger.data.local.source.CurrencyLocalDataSourceImpl
import com.maksimilian.currencyexchanger.data.network.source.CurrencyNetworkDataSource
import com.maksimilian.currencyexchanger.data.network.source.CurrencyNetworkDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {
    @Binds
    fun bindCurrencyNetworkDataSource(impl: CurrencyNetworkDataSourceImpl): CurrencyNetworkDataSource

    @Binds
    fun bindCurrencyLocalDataSource(impl: CurrencyLocalDataSourceImpl): CurrencyLocalDataSource
}