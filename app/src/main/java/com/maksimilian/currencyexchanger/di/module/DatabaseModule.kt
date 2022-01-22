package com.maksimilian.currencyexchanger.di.module

import android.content.Context
import androidx.room.Room
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyAccountDao
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
}