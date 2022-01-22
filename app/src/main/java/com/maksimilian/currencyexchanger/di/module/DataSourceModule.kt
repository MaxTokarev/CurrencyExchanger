package com.maksimilian.currencyexchanger.di.module

import com.maksimilian.currencyexchanger.data.local.source.CurrencyLocalDataSource
import com.maksimilian.currencyexchanger.data.local.source.CurrencyLocalDataSourceImpl
import com.maksimilian.currencyexchanger.data.local.source.user.CurrencyAccountsLocalDataSource
import com.maksimilian.currencyexchanger.data.local.source.user.CurrencyAccountsLocalDataSourceImpl
import com.maksimilian.currencyexchanger.data.network.source.account.CurrencyAccountNetworkDataSource
import com.maksimilian.currencyexchanger.data.network.source.CurrencyNetworkDataSource
import com.maksimilian.currencyexchanger.data.network.source.CurrencyNetworkDataSourceImpl
import com.maksimilian.currencyexchanger.data.network.source.account.MockCurrencyAccountNetworkDataSource
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {
    @Binds
    fun bindCurrencyNetworkDataSource(impl: CurrencyNetworkDataSourceImpl):
        CurrencyNetworkDataSource

    @Binds
    fun bindCurrencyLocalDataSource(impl: CurrencyLocalDataSourceImpl): CurrencyLocalDataSource

    @Binds
    fun bindAccountsLocalDataSource(impl: CurrencyAccountsLocalDataSourceImpl):
        CurrencyAccountsLocalDataSource

    @Binds
    fun bindAccountsNetworkDataSource(impl: MockCurrencyAccountNetworkDataSource):
        CurrencyAccountNetworkDataSource
}