package com.maksimilian.currencyexchanger.domain.repository

import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.data.network.model.CurrencyRatesData
import io.reactivex.Observable
import io.reactivex.Single

interface CurrencyRepository {
    fun getCurrencies(): Single<List<CurrencyData>>

    fun observeCurrencyRates(): Observable<CurrencyRatesData>
}