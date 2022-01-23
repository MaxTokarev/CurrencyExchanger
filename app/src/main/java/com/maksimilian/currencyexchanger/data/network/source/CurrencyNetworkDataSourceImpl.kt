package com.maksimilian.currencyexchanger.data.network.source

import com.maksimilian.currencyexchanger.common.mapList
import com.maksimilian.currencyexchanger.data.mappers.CurrencyMapperApiToData
import com.maksimilian.currencyexchanger.data.mappers.CurrencyRatesMapperApiToData
import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.data.network.api.CurrencyApiService
import com.maksimilian.currencyexchanger.data.network.model.CurrencyRatesData
import com.maksimilian.currencyexchanger.data.network.source.account.AccountCurrency
import com.maksimilian.currencyexchanger.data.network.source.account.AccountCurrency.Companion.id
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class CurrencyNetworkDataSourceImpl @Inject constructor(
    private val service: CurrencyApiService,
    private val currencyMapperApiToData: CurrencyMapperApiToData,
    private val currencyRatesMapperApiToData: CurrencyRatesMapperApiToData
) : CurrencyNetworkDataSource {

    override fun fetchCurrencies(): Single<List<CurrencyData>> {
        return Single.just(AccountCurrency.values())
            .map { currencies -> currencies.map { it.id to Currency.getInstance(it.code) } }
            .map { currencyMapperApiToData.mapList(it) }
    }

    override fun fetchCurrencyRates(currencies: List<CurrencyData>): Single<CurrencyRatesData> {
        return service.fetchLatestCurrency()
            .map { currencyRatesMapperApiToData.map(currencies to it) }
    }
}