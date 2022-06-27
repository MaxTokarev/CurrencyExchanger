package com.maksimilian.currencyexchanger.ui.model

data class CurrencyAccountUi(
    val id: Int,
    val currencyId: Int,
    val name: String,
    val balance: String,
    val symbol: String,
    val shortName: String
)