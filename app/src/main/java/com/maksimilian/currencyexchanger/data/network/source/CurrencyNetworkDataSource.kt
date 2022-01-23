package com.maksimilian.currencyexchanger.data.network.source

import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.data.network.model.CurrencyRatesData
import io.reactivex.Single

interface CurrencyNetworkDataSource {
    fun fetchCurrencies(): Single<List<CurrencyData>>
    fun fetchCurrencyRates(currencies: List<CurrencyData>): Single<CurrencyRatesData>
}