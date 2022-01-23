package com.maksimilian.currencyexchanger.domain.usecase.account.fetch

import com.maksimilian.currencyexchanger.domain.repository.CurrencyRepository
import com.maksimilian.currencyexchanger.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class FetchIfRequireUserAccountsUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val currencyRepository: CurrencyRepository
) : FetchIfRequireUserAccountsUseCase {
    override fun invoke(): Completable {
        return currencyRepository.getCurrencies()
            .flatMapCompletable { userRepository.fetchIfRequireAccounts(it) }
    }
}