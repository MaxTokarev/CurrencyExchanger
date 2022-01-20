package com.maksimilian.currencyexchanger.ui.mvi

import com.maksimilian.currencyexchanger.ui.CurrencyAccountUi

data class ConverterState(
    val fromAccounts: List<CurrencyAccountUi> = emptyList(),
    val toAccounts: List<CurrencyAccountUi> = emptyList(),
    val fromCurrencyName: String = "",
    val toCurrencyName: String = "",
    val currencyRate: Double = 0.0
)