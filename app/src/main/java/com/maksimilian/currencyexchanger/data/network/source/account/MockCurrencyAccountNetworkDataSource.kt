package com.maksimilian.currencyexchanger.data.network.source.account

import com.maksimilian.currencyexchanger.data.models.CurrencyAccountData
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Mock realization of getting accounts
 * You can add your currency simply by adding to [AccountCurrency] your symbol
 */
class MockCurrencyAccountNetworkDataSource @Inject constructor() :
    CurrencyAccountNetworkDataSource {
    override fun fetchUserAccounts(): Single<List<CurrencyAccountData>> {
        val accounts = AccountCurrency.values()
            .mapIndexed { index, accountSymbol ->
                val currency = Currency.getInstance(accountSymbol.symbol)
                CurrencyAccountData(
                    id = index,
                    name = currency.displayName,
                    balance = INITIAL_BALANCE,
                    symbol = currency.symbol
                )
            }
        return Single.just(accounts)
    }

    companion object {
        private const val INITIAL_BALANCE = 100.0
    }
}