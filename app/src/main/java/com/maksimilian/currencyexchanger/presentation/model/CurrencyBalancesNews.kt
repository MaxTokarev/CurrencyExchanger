package com.maksimilian.currencyexchanger.presentation.model

sealed class CurrencyBalancesNews {
    class Error(val throwable: Throwable) : CurrencyBalancesNews()
    object SuccessExchange : CurrencyBalancesNews()
}