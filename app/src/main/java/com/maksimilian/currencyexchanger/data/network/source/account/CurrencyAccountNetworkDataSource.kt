package com.maksimilian.currencyexchanger.data.network.source.account

import com.maksimilian.currencyexchanger.data.models.CurrencyAccountData
import com.maksimilian.currencyexchanger.data.models.CurrencyData
import io.reactivex.Single

interface CurrencyAccountNetworkDataSource {
    fun fetchUserAccountsFor(currencies: List<CurrencyData>): Single<List<CurrencyAccountData>>
}