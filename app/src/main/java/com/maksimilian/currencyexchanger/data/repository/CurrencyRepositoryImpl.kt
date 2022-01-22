package com.maksimilian.currencyexchanger.data.repository

import com.maksimilian.currencyexchanger.data.local.source.CurrencyLocalDataSource
import com.maksimilian.currencyexchanger.data.network.source.CurrencyNetworkDataSource
import com.maksimilian.currencyexchanger.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val networkDataSource: CurrencyNetworkDataSource,
    private val localDataSource: CurrencyLocalDataSource
): CurrencyRepository {
}