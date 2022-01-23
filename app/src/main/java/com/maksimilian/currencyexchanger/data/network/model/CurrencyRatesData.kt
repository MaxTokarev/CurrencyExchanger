package com.maksimilian.currencyexchanger.data.network.model

import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.domain.model.Currency

class CurrencyRatesData(
    val lastSyncTimestamp: Long,
    val base: CurrencyData,
    val rates: List<CurrencyRateData>
)

class CurrencyRateData(
    val currency: CurrencyData,
    val rate: Double
)