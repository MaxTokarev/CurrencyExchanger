package com.maksimilian.currencyexchanger.presentation

import com.badoo.mvicore.element.Reducer
import com.maksimilian.currencyexchanger.domain.model.CurrencyRate
import com.maksimilian.currencyexchanger.domain.usecase.account.CalculateRateUseCase
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
                currentRate = CalculateRateUseCase.Base(1.0)
                    .calculate(
                        state.currentRate(effect.fromAccount),
                        state.currentRate(effect.toAccount)
                    )
            )
            is CurrencyBalancesEffect.UpdateAccountsCount -> state.copy(
                fromAccountCount = effect.fromAccount,
                toAccountCount = CalculateRateUseCase.Base(effect.fromAccount)
                    .calculate(
                        state.currentRate(state.fromAccountPosition),
                        state.currentRate(state.toAccountPosition)
                    )
            )
            is CurrencyBalancesEffect.FailExchange -> state.copy(isExchangeLoading = false)
            CurrencyBalancesEffect.StartExchangeLoading -> state.copy(isExchangeLoading = true)
            CurrencyBalancesEffect.SuccessExchange -> state.copy(isExchangeLoading = false)
        }

    private fun CurrencyBalancesState.currentRate(position: Int) =
        rates.getCurrentRate(accounts[position].currencyId)?.rate ?: 0.0

    private fun List<CurrencyRate>.getCurrentRate(fromAccountId: Int) =
        find { rate -> rate.currency.id == fromAccountId }
}