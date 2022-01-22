package com.maksimilian.currencyexchanger.ui.mvi

import com.maksimilian.currencyexchanger.ui.CurrencyAccountUi

class MainViewModel(
    val isAccountsLoading: Boolean = false,
    val fromAccounts: List<CurrencyAccountUi> = emptyList(),
    val toAccounts: List<CurrencyAccountUi> = emptyList(),
    val fromCurrencyName: String = "",
    val toCurrencyName: String = "",
    val currencyRate: Double = 0.0,
    val isExchangeLoading: Boolean
)

class ViewModelTransformer : (CurrencyBalancesFeature.State) -> MainViewModel {
    override fun invoke(state: CurrencyBalancesFeature.State): MainViewModel {
        return MainViewModel(
            isAccountsLoading = state.isLoading,
            fromAccounts = state.fromAccounts,
            toAccounts = state.toAccounts,
            fromCurrencyName = state.fromCurrencyName,
            toCurrencyName = state.toCurrencyName,
            isExchangeLoading = false
        )
    }
}