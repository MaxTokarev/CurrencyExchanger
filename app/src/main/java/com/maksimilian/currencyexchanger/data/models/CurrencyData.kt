package com.maksimilian.currencyexchanger.data.models

/**
 * Currency model for data layer
 */
class CurrencyData(
    val id: Int,
    val name: String,
    val symbol: String,
    val shortName: String
)