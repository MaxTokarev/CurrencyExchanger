package com.maksimilian.currencyexchanger.domain.usecase.account

import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import com.maksimilian.currencyexchanger.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

interface GetAccountByIdUseCase: (Int) -> Single<CurrencyAccount>

class GetAccountByIdUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetAccountByIdUseCase {
    override fun invoke(id: Int): Single<CurrencyAccount> =
        userRepository.getAllAccounts()
            .map { accounts ->
                requireNotNull(accounts.firstOrNull { account -> account.id == id }) {
                    "Cannot find account by given id - $id"
                }
            }
}