package com.maksimilian.currencyexchanger.data.network.source.account

import com.maksimilian.currencyexchanger.data.models.CurrencyAccountData
import com.maksimilian.currencyexchanger.data.models.CurrencyData
import io.reactivex.Single
import javax.inject.Inject

/**
 * Mock realization of getting accounts
 */
class MockCurrencyAccountNetworkDataSource @Inject constructor() :
    CurrencyAccountNetworkDataSource {
    override fun fetchUserAccountsFor(
        currencies: List<CurrencyData>
    ): Single<List<CurrencyAccountData>> {
        val accounts = currencies.mapIndexed { index, currencyData ->
            CurrencyAccountData(
                id = index,
                currency = currencyData,
                balance = INITIAL_BALANCE
            )
        }
        return Single.just(accounts)
    }

    companion object {
        private const val INITIAL_BALANCE = 100.0
    }
}