package com.maksimilian.currencyexchanger.data.local.source.user

import com.maksimilian.currencyexchanger.data.models.CurrencyAccountData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface CurrencyAccountsLocalDataSource {
    fun getAllAccounts(): Single<List<CurrencyAccountData>>

    /**
     * If database have accounts then they updates otherwise they will be inserted
     */
    fun upInsertAccounts(accounts: List<CurrencyAccountData>): Completable

    /**
     * Observes user accounts
     */
    fun observeAccounts(): Observable<List<CurrencyAccountData>>

    fun updateBalance(accountId: Int, newBalance: Double): Completable
}