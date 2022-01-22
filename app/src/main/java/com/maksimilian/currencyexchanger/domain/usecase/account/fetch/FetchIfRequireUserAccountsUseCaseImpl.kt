package com.maksimilian.currencyexchanger.domain.usecase.account.fetch

import com.maksimilian.currencyexchanger.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class FetchIfRequireUserAccountsUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchIfRequireUserAccountsUseCase {
    override fun invoke(): Completable {
        return userRepository.fetchIfRequireAccounts()
    }
}