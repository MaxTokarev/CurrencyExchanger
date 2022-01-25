package com.maksimilian.currencyexchanger.domain.repository

import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun fetchIfRequireAccounts(currencies: List<CurrencyData>): Completable
    fun observeAccounts(): Observable<List<CurrencyAccount>>
    fun getAllAccounts(): Single<List<CurrencyAccount>>
}