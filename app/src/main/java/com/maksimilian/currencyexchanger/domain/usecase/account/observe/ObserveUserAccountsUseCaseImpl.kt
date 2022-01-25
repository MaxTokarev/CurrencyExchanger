package com.maksimilian.currencyexchanger.domain.usecase.account.observe

import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import com.maksimilian.currencyexchanger.domain.repository.UserRepository
import com.maksimilian.currencyexchanger.domain.usecase.account.fetch.FetchIfRequireUserAccountsUseCase
import com.maksimilian.currencyexchanger.domain.usecase.account.fetch.FetchIfRequireUserAccountsUseCaseImpl
import io.reactivex.Observable
import javax.inject.Inject

class ObserveUserAccountsUseCaseImpl @Inject constructor(
    private val fetchIfRequireUserAccountsUseCase: FetchIfRequireUserAccountsUseCase,
    private val userRepository: UserRepository
): ObserveUserAccountsUseCase {
    override fun invoke(): Observable<List<CurrencyAccount>> {
        return fetchIfRequireUserAccountsUseCase()
            .andThen(userRepository.observeAccounts())
    }
}