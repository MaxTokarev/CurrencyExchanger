package com.maksimilian.currencyexchanger.data.network

import com.maksimilian.currencyexchanger.data.network.model.CurrencyApiResponse
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("") // TODO: 17.01.2022 Finish this up
    fun fetchLatestCurrency(): Single<CurrencyApiResponse>
}