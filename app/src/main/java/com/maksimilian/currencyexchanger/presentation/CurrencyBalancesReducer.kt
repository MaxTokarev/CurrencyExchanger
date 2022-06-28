package com.maksimilian.currencyexchanger.presentation

import com.badoo.mvicore.element.Reducer
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesEffect
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesState

class CurrencyBalancesReducer : Reducer<CurrencyBalancesState, CurrencyBalancesEffect> {
    override fun invoke(
        state: CurrencyBalancesState,
        effect: CurrencyBalancesEffect
    ): CurrencyBalancesState =
        when (effect) {
            is CurrencyBalancesEffect.ErrorLoading -> state.copy(isLoading = false)
            is CurrencyBalancesEffect.LoadedData -> state.copy(
                isLoading = false,
                accounts = effect.accounts,
                rates = effect.rates.rates
            )
            CurrencyBalancesEffect.StartLoading -> state.copy(isLoading = true)
            is CurrencyBalancesEffect.UpdateAccountsPosition -> state.copy(
                toAccountPosition = effect.toAccount,
                fromAccountPosition = effect.fromAccount,
                currentRate = effect.currentRate
            )
            is CurrencyBalancesEffect.UpdateAccountsCount -> state.copy(
                fromAccountCount = effect.fromAccount,
                toAccountCount = effect.toAccount
            )
            is CurrencyBalancesEffect.FailExchange -> state.copy(isExchangeLoading = false)
            CurrencyBalancesEffect.StartExchangeLoading -> state.copy(isExchangeLoading = true)
            CurrencyBalancesEffect.SuccessExchange -> state.copy(isExchangeLoading = false)
        }
}