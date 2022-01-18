package com.maksimilian.currencyexchanger.data.network.source

import com.maksimilian.currencyexchanger.data.network.api.CurrencyApiService
import javax.inject.Inject

class CurrencyNetworkDataSourceImpl @Inject constructor(
    private val service: CurrencyApiService
): CurrencyNetworkDataSource {
}