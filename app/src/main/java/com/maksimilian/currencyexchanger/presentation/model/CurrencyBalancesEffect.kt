package com.maksimilian.currencyexchanger.presentation.model

import com.maksimilian.currencyexchanger.domain.model.CurrencyRates
import com.maksimilian.currencyexchanger.ui.model.CurrencyAccountUi

sealed class CurrencyBalancesEffect {
    object StartLoading : CurrencyBalancesEffect()
    object StartExchangeLoading : CurrencyBalancesEffect()
    object SuccessExchange : CurrencyBalancesEffect()
    class FailExchange(val throwable: Throwable) : CurrencyBalancesEffect()
    class LoadedData(val accounts: List<CurrencyAccountUi>, val rates: CurrencyRates) : CurrencyBalancesEffect()
    class UpdateAccountsPosition(val fromAccount: Int, val toAccount: Int) : CurrencyBalancesEffect()
    class UpdateAccountsCount(val fromAccount: Double, val toAccount: Double) : CurrencyBalancesEffect()
    class ErrorLoading(val throwable: Throwable) : CurrencyBalancesEffect()
}
