package com.maksimilian.currencyexchanger.domain.model

class CurrencyAccount(
    val id: Int,
    val name: String,
    val balance: Double,
    val symbol: String,
    val shortName: String
)