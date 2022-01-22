package com.maksimilian.currencyexchanger.domain.usecase.account.fetch

import io.reactivex.Completable
import javax.inject.Inject

interface FetchIfRequireUserAccountsUseCase {
    operator fun invoke(): Completable
}