package com.maksimilian.currencyexchanger.data.local.source

import com.maksimilian.currencyexchanger.data.local.db.AppDatabase
import javax.inject.Inject

class CurrencyLocalDataSourceImpl @Inject constructor(
    private val database: AppDatabase
): CurrencyLocalDataSource {
}