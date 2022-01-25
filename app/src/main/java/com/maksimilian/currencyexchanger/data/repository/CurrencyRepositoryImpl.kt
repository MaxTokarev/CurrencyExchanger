package com.maksimilian.currencyexchanger.data.repository

import com.maksimilian.currencyexchanger.data.local.source.CurrencyLocalDataSource
import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.data.network.model.CurrencyRatesData
import com.maksimilian.currencyexchanger.data.network.source.CurrencyNetworkDataSource
import com.maksimilian.currencyexchanger.domain.repository.CurrencyRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val networkDataSource: CurrencyNetworkDataSource,
    private val localDataSource: CurrencyLocalDataSource
) : CurrencyRepository {
    override fun getCurrencies(): Single<List<CurrencyData>> =
        localDataSource.getAllCurrencies()
            .flatMap { localCurrencies ->
                if (localCurrencies.isEmpty()) fetchAndInsertCurrencies()
                else Single.just(localCurrencies)
            }

    override fun fetchCurrencyRates(): Single<CurrencyRatesData> {
        return getCurrencies()
            .flatMap { networkDataSource.fetchCurrencyRates(it) }
            .flatMap { localDataSource.upInsertCurrencyRates(it).andThen(Single.just(it)) }
    }

    override fun observeCurrencyRates(): Observable<CurrencyRatesData> =
        localDataSource.observeCurrencyRates()

    private fun fetchAndInsertCurrencies(): Single<List<CurrencyData>> =
        networkDataSource.fetchCurrencies()
            .flatMap { currencies ->
                localDataSource.upInsertCurrencies(currencies)
                    .andThen(Single.just(currencies))
            }
}