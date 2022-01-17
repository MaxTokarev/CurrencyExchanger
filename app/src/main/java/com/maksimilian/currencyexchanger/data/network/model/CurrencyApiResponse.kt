package com.maksimilian.currencyexchanger.data.network.model

class CurrencyApiResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: RatesApiResponse
)