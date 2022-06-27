package com.maksimilian.currencyexchanger.ui.mvi

import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesState
import com.maksimilian.currencyexchanger.ui.CurrencyAccountUi
import kotlin.math.round

class MainViewModel(
    val isAccountsLoading: Boolean,
    val currentFromAccount: CurrencyAccountUi?,
    val currentToAccount: CurrencyAccountUi?,
    val currencyRate: Double,
    val fromAccountCount: String,
    val toAccountCount: String,
    val isExchangeLoading: Boolean,
    val accounts: List<CurrencyAccountUi>,
)

class ViewModelTransformer :
        (Pair<CurrencyBalancesState, ExchangeFeature.State>) -> MainViewModel {
    override fun invoke(pair: Pair<CurrencyBalancesState, ExchangeFeature.State>): MainViewModel {
        val (currencyState, exchangeState) = pair
        return MainViewModel(
            isAccountsLoading = currencyState.isLoading,
            accounts = currencyState.accounts,
            isExchangeLoading = exchangeState.isLoading,
            currencyRate = currencyState.currentRate,
            currentToAccount = currencyState.accounts.getOrNull(currencyState.toAccountPosition),
            currentFromAccount = currencyState.accounts.getOrNull(currencyState.fromAccountPosition),
            fromAccountCount =
            if (currencyState.fromAccountCount == 0.0) ""
            else currencyState.fromAccountCount.round(2).toString(),
            toAccountCount =
            if (currencyState.toAccountCount == 0.0) ""
            else currencyState.toAccountCount.round(2).toString()
        )
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}