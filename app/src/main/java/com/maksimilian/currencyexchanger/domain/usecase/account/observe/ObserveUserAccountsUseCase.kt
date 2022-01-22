package com.maksimilian.currencyexchanger.domain.usecase.account.observe

import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import io.reactivex.Observable

interface ObserveUserAccountsUseCase {
    operator fun invoke(): Observable<List<CurrencyAccount>>
}