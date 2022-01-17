package com.maksimilian.currencyexchanger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maksimilian.currencyexchanger.data.db.entity.CurrencyAccountEntity

@Database(
    entities = [
        CurrencyAccountEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val NAME = "currency-converter"
    }
}