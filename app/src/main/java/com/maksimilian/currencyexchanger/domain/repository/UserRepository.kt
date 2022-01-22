package com.maksimilian.currencyexchanger.domain.repository

import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {
    fun fetchIfRequireAccounts(): Completable
    fun observeAccounts(): Observable<List<CurrencyAccount>>
}