package com.maksimilian.currencyexchanger.di.module

import android.content.Context
import androidx.room.Room
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyAccountDao
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyDao
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyRateDao
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyRatesDao
import com.maksimilian.currencyexchanger.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(applicationContext: Context): AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        AppDatabase.NAME
    ).build()

    @Provides
    @Singleton
    fun provideAccountsDao(database: AppDatabase): CurrencyAccountDao =
        database.currencyAccountDao()

    @Provides
    @Singleton
    fun provideRateDao(database: AppDatabase): CurrencyRateDao =
        database.currencyRateDao()

    @Provides
    @Singleton
    fun provideCurrencyDao(database: AppDatabase): CurrencyDao =
        database.currencyDao()

    @Provides
    @Singleton
    fun provideCurrencyRatesDao(database: AppDatabase): CurrencyRatesDao =
        database.currencyRatesDao()
}