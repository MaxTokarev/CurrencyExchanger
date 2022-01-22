package com.maksimilian.currencyexchanger.di.module

import com.maksimilian.currencyexchanger.domain.usecase.account.fetch.FetchIfRequireUserAccountsUseCase
import com.maksimilian.currencyexchanger.domain.usecase.account.fetch.FetchIfRequireUserAccountsUseCaseImpl
import com.maksimilian.currencyexchanger.domain.usecase.account.observe.ObserveUserAccountsUseCase
import com.maksimilian.currencyexchanger.domain.usecase.account.observe.ObserveUserAccountsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun bindObserveUserAccount(
        impl: ObserveUserAccountsUseCaseImpl
    ): ObserveUserAccountsUseCase

    @Binds
    fun bindFetchUserAccount(
        impl: FetchIfRequireUserAccountsUseCaseImpl
    ): FetchIfRequireUserAccountsUseCase
}