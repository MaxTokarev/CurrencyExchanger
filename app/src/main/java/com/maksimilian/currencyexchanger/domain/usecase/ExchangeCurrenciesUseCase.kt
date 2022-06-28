package com.maksimilian.currencyexchanger.domain.usecase

import com.maksimilian.currencyexchanger.domain.repository.UserRepository
import com.maksimilian.currencyexchanger.domain.usecase.account.GetAccountByIdUseCase
import io.reactivex.Single
import javax.inject.Inject

interface ExchangeCurrenciesUseCase : (Int, Int, Double) -> Single<Result<Unit>>

class ExchangeCurrenciesUseCaseImpl @Inject constructor(
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val userRepository: UserRepository
) : ExchangeCurrenciesUseCase {
    override fun invoke(fromAccountId: Int, toAccountId: Int, count: Double): Single<Result<Unit>> {
        return getAccountByIdUseCase(fromAccountId)
            .zipWith(getAccountByIdUseCase(toAccountId)) { fromAccount, toAccount ->
                require(fromAccount.balance < count) { "count" }
                require(fromAccount.id != toAccount.id) { "id is equal" }
                fromAccount to toAccount
            }
            .flatMapCompletable { (fromAccount, toAccount) ->
                val newBalance = fromAccount.balance - count
                userRepository.updateBalanceOnAccount(fromAccount.id, newBalance)
                    .andThen(userRepository.updateBalanceOnAccount(toAccountId, newBalance))
            }
            .andThen(Single.just(Result.success(Unit)))
            .onErrorReturn { Result.failure(it) }
    }
}