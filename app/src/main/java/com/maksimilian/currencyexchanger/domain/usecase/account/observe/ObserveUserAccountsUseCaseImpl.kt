package com.maksimilian.currencyexchanger.domain.usecase.account.observe

import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import com.maksimilian.currencyexchanger.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class ObserveUserAccountsUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): ObserveUserAccountsUseCase {
    override fun invoke(): Observable<List<CurrencyAccount>> {
        return userRepository.observeAccounts()
    }
}