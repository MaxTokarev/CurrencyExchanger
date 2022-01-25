package com.maksimilian.currencyexchanger.data.network.api

import com.maksimilian.currencyexchanger.data.network.model.CurrencyRateApiResponse
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("latest")
    fun fetchLatestCurrency(): Single<CurrencyRateApiResponse>
}