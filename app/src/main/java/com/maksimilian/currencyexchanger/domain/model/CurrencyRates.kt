package com.maksimilian.currencyexchanger.domain.model

import kotlinx.datetime.Instant

class CurrencyRates(
    val lastSyncTime: Instant,
    val baseCurrency: Currency,
    val rates: List<CurrencyRate>
)

class CurrencyRate(
    val currency: Currency,
    val rate: Double
)