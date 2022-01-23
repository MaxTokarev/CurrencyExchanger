package com.maksimilian.currencyexchanger.domain.repository

import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {
    fun fetchIfRequireAccounts(currencies: List<CurrencyData>): Completable
    fun observeAccounts(): Observable<List<CurrencyAccount>>
}