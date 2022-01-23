package com.maksimilian.currencyexchanger.data.network.model

class CurrencyRateApiResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String, // api returns currency code for example EURO
    val date: String,
    val rates: RatesApiResponse
)