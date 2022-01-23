package com.maksimilian.currencyexchanger.ui.mvi

import com.maksimilian.currencyexchanger.ui.CurrencyAccountUi

class MainViewModel(
    val isAccountsLoading: Boolean = false,
    val fromAccounts: List<CurrencyAccountUi> = emptyList(),
    val toAccounts: List<CurrencyAccountUi> = emptyList(),
    val fromCurrencyShortName: String = "",
    val toCurrencyShortName: String = "",
    val currencyRate: Double = 0.0,
    val isExchangeLoading: Boolean,
)

class ViewModelTransformer : (CurrencyBalancesFeature.State) -> MainViewModel {
    override fun invoke(state: CurrencyBalancesFeature.State): MainViewModel {
        return MainViewModel(
            isAccountsLoading = state.isLoading,
            fromAccounts = state.fromAccounts,
            toAccounts = state.toAccounts,
            isExchangeLoading = false,
            currencyRate = 0.0,
            toCurrencyShortName = state.accounts.getOrNull(state.toAccountPosition)?.shortName ?: "",
            fromCurrencyShortName = state.accounts.getOrNull(state.fromAccountPosition)?.shortName ?: ""
        )
    }
}