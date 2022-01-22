package com.maksimilian.currencyexchanger.data.local.source.user

import com.maksimilian.currencyexchanger.data.models.CurrencyAccountData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface CurrencyAccountsLocalDataSource {
    fun getAllAccounts(): Single<List<CurrencyAccountData>>
    fun upInsertAccounts(accounts: List<CurrencyAccountData>): Completable
    fun observeAccounts(): Observable<List<CurrencyAccountData>>
}