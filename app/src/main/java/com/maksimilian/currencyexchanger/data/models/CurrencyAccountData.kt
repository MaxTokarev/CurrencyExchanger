package com.maksimilian.currencyexchanger.data.models

/**
 * Account model for data layer
 */
class CurrencyAccountData(
    val id: Int,
    val currency: CurrencyData,
    val balance: Double,
)