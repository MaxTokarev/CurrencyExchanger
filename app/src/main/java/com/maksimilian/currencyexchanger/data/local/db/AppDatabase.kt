package com.maksimilian.currencyexchanger.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyAccountDao
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyDao
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyRateDao
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyRatesDao
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyAccountEntity
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyEntity
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRateEntity
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRatesEntity

@Database(
    entities = [
        CurrencyAccountEntity::class,
        CurrencyRateEntity::class,
        CurrencyRatesEntity::class,
        CurrencyEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyAccountDao(): CurrencyAccountDao
    abstract fun currencyRateDao(): CurrencyRateDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun currencyRatesDao(): CurrencyRatesDao

    companion object {
        const val NAME = "currency-converter"
    }
}