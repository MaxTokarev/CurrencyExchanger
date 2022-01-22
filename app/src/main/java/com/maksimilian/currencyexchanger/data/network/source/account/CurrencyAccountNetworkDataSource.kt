package com.maksimilian.currencyexchanger.data.network.source.account

import com.maksimilian.currencyexchanger.data.models.CurrencyAccountData
import io.reactivex.Single

interface CurrencyAccountNetworkDataSource {
    fun fetchUserAccounts(): Single<List<CurrencyAccountData>>
}