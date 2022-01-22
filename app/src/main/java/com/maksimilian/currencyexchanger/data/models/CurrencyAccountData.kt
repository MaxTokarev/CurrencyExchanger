package com.maksimilian.currencyexchanger.data.models

/**
 * Account model for data layer
 */
class CurrencyAccountData(
    val id: Int,
    val name: String,
    val balance: Double,
    val symbol: String
)