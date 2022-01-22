package com.maksimilian.currencyexchanger.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyAccountDao
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyAccountEntity

@Database(
    entities = [
        CurrencyAccountEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyAccountDao(): CurrencyAccountDao

    companion object {
        const val NAME = "currency-converter"
    }
}