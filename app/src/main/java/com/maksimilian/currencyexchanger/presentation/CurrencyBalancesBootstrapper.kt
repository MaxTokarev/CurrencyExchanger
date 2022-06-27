package com.maksimilian.currencyexchanger.presentation

import com.badoo.mvicore.element.Bootstrapper
import com.maksimilian.currencyexchanger.domain.usecase.ObserveCurrencyRatesUseCase
import com.maksimilian.currencyexchanger.domain.usecase.account.observe.ObserveUserAccountsUseCase
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesWish
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyBalancesBootstrapper @Inject constructor(
    private val observeUserAccountsUseCase: ObserveUserAccountsUseCase,
    private val observeCurrencyRatesUseCase: ObserveCurrencyRatesUseCase
) : Bootstrapper<CurrencyBalancesWish> {
    override fun invoke(): Observable<CurrencyBalancesWish> {
        return Observable.zip(
            observeUserAccountsUseCase(),
            observeCurrencyRatesUseCase()
        ) { accounts, rates -> accounts to rates }
            .subscribeOn(Schedulers.io()) // TODO: Provide Schedulers
            .observeOn(AndroidSchedulers.mainThread())
            .map<CurrencyBalancesWish> { (accounts, rates) ->
                CurrencyBalancesWish.UpdateData(accounts, rates)
            }
            .onErrorReturn { CurrencyBalancesWish.Error(it) }
    }
}