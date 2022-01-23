package com.maksimilian.currencyexchanger.domain.model

import kotlinx.datetime.LocalDateTime

class CurrencyRates(
    val lastSyncTime: LocalDateTime,
    val baseCurrency: Currency,
    val rates: List<Currency>
)

class CurrencyRate(
    val currency: Currency,
    val rate: Rate
)

@JvmInline
value class Rate(val value: Double)