package com.maksimilian.currencyexchanger.data.network.model

import com.google.gson.annotations.SerializedName

class RatesApiResponse(
    @SerializedName("EUR")
    val euroRate: Double,
    @SerializedName("USD")
    val usdRate: Double,
    @SerializedName("GBP")
    val gbpRate: Double
)