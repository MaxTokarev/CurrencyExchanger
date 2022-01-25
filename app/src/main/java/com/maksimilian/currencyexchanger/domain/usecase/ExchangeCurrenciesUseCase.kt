package com.maksimilian.currencyexchanger.domain.usecase

import com.maksimilian.currencyexchanger.domain.repository.CurrencyRepository
import com.maksimilian.currencyexchanger.domain.usecase.account.GetAccountByIdUseCase
import io.reactivex.Single
import javax.inject.Inject

interface ExchangeCurrenciesUseCase {
    operator fun invoke(fromAccount: Int, toAccount: Int, count: Double, rate: Double)

    class Base @Inject constructor(
        private val getAccountByIdUseCase: GetAccountByIdUseCase,
        private val currencyRepository: CurrencyRepository
    ) : ExchangeCurrenciesUseCase {
        override operator fun invoke(
            fromAccountId: Int,
            toAccountId: Int,
            count: Double,
            rate: Double
        ) = Unit
//            getAccountByIdUseCase(fromAccountId)
//            .zipWith(getAccountByIdUseCase(toAccountId)) { fromAccount, toAccount ->
//                require(fromAccount.balance < count) { "count" }
//
//            }
    }
}