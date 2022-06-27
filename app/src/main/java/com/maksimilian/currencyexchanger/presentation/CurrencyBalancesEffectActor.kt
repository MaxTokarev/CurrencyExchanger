package com.maksimilian.currencyexchanger.presentation

import com.badoo.mvicore.element.Actor
import com.maksimilian.currencyexchanger.common.mapList
import com.maksimilian.currencyexchanger.domain.usecase.ExchangeCurrenciesUseCase
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesEffect
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesState
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesWish
import com.maksimilian.currencyexchanger.presentation.mapper.CurrencyAccountMapperDomainToUi
import io.reactivex.Observable
import javax.inject.Inject

class CurrencyBalancesEffectActor @Inject constructor(
    private val exchangeCurrenciesUseCase: ExchangeCurrenciesUseCase.Base
) : Actor<CurrencyBalancesState, CurrencyBalancesWish, CurrencyBalancesEffect> {
    override fun invoke(
        state: CurrencyBalancesState,
        wish: CurrencyBalancesWish
    ): Observable<out CurrencyBalancesEffect> = when (wish) {
        is CurrencyBalancesWish.UpdateData -> Observable.just(wish)
            .map { CurrencyAccountMapperDomainToUi().mapList(it.accounts) }
            .map { CurrencyBalancesEffect.LoadedData(it, wish.rates) }
        is CurrencyBalancesWish.Error ->
            Observable.just(CurrencyBalancesEffect.ErrorLoading(wish.throwable))
        is CurrencyBalancesWish.ToAccountPositionUpdate -> Observable.just(
            CurrencyBalancesEffect.UpdateAccountsPosition(state.fromAccountPosition, wish.position)
        )
        is CurrencyBalancesWish.FromAccountPositionUpdate -> Observable.just(
            CurrencyBalancesEffect.UpdateAccountsPosition(wish.position, state.toAccountPosition)
        )
        is CurrencyBalancesWish.FromAccountCountUpdate -> Observable.just(
            CurrencyBalancesEffect.UpdateAccountsCount(
                fromAccount = wish.value.toDoubleOrNull() ?: 0.0,
                toAccount = state.toAccountCount
            )
        )
        is CurrencyBalancesWish.ToAccountCountUpdate -> Observable.just(
            CurrencyBalancesEffect.UpdateAccountsCount(
                fromAccount = state.fromAccountCount,
                toAccount = wish.value.toDoubleOrNull() ?: 0.0
            )
        )
        CurrencyBalancesWish.Exchange -> Observable.just(CurrencyBalancesEffect.StartExchangeLoading)
            .flatMapSingle {
                exchangeCurrenciesUseCase(
                    state.accounts[state.fromAccountPosition].id,
                    state.accounts[state.toAccountPosition].id,
                    state.fromAccountCount
                )
            }.map { exchangeResult ->
                exchangeResult.exceptionOrNull()?.let { CurrencyBalancesEffect.FailExchange(it) }
                exchangeResult.getOrNull()?.let { CurrencyBalancesEffect.SuccessExchange }
            }
    }
}