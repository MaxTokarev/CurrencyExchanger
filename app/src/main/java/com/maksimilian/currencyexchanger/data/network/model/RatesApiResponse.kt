package com.maksimilian.currencyexchanger.data.network.model

import com.google.gson.annotations.SerializedName

class RatesApiResponse(
    @SerializedName("EUR")
    val euro: Double,
    @SerializedName("USD")
    val usd: Double,
    @SerializedName("GBP")
    val gbp: Double,
    @SerializedName("RUB")
    val rub: Double
)