package com.maksimilian.currencyexchanger.data.local.source

import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.data.network.model.CurrencyRatesData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface CurrencyLocalDataSource {
    fun getAllCurrencies(): Single<List<CurrencyData>>
    fun upInsertCurrencies(currencies: List<CurrencyData>): Completable

    fun upInsertCurrencyRates(currencyRatesData: CurrencyRatesData): Completable
    fun observeCurrencyRates(): Observable<CurrencyRatesData>
}