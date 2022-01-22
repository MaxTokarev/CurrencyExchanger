package com.maksimilian.currencyexchanger.di.module

import com.maksimilian.currencyexchanger.data.repository.CurrencyRepositoryImpl
import com.maksimilian.currencyexchanger.data.repository.UserRepositoryImpl
import com.maksimilian.currencyexchanger.domain.repository.CurrencyRepository
import com.maksimilian.currencyexchanger.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindCurrencyRepository(impl: CurrencyRepositoryImpl): CurrencyRepository

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}