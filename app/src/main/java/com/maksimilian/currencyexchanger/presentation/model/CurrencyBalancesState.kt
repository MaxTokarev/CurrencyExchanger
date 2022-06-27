package com.maksimilian.currencyexchanger.presentation.model

import com.maksimilian.currencyexchanger.domain.model.CurrencyRate
import com.maksimilian.currencyexchanger.ui.model.CurrencyAccountUi

data class CurrencyBalancesState(
    val isLoading: Boolean = false,
    val isExchangeLoading: Boolean = false,
    val accounts: List<CurrencyAccountUi> = emptyList(),
    val fromAccountPosition: Int = 0,
    val toAccountPosition: Int = 1,

    val fromAccountCount: Double = 0.0,
    val toAccountCount: Double = 0.0,

    val currentRate: Double = 0.0,
    val rates: List<CurrencyRate> = emptyList()
)