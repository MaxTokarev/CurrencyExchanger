package com.maksimilian.currencyexchanger.presentation

import com.badoo.mvicore.element.Actor
import com.maksimilian.currencyexchanger.common.mapList
import com.maksimilian.currencyexchanger.domain.model.CurrencyRate
import com.maksimilian.currencyexchanger.domain.usecase.ExchangeCurrenciesUseCase
import com.maksimilian.currencyexchanger.domain.usecase.account.CalculateRateUseCase
import com.maksimilian.currencyexchanger.presentation.mapper.CurrencyAccountMapperDomainToUi
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesEffect
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesState
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesWish
import io.reactivex.Observable
import javax.inject.Inject

private const val DEFAULT_EXCHANGE_COUNT = 1.0

class CurrencyBalancesEffectActor @Inject constructor(
    private val exchangeCurrenciesUseCase: ExchangeCurrenciesUseCase,
    private val calculateRateUseCase: CalculateRateUseCase
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
            toAccountPositionUpdate(state, wish)
        )
        is CurrencyBalancesWish.FromAccountPositionUpdate -> Observable.just(
            fromAccountPositionUpdate(state, wish)
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

    private fun toAccountPositionUpdate(
        state: CurrencyBalancesState,
        wish: CurrencyBalancesWish.ToAccountPositionUpdate
    ): CurrencyBalancesEffect.UpdateAccountsPosition {
        val fromAccountPosition = calculateUnequalPosition(
            wish.position,
            state.fromAccountPosition,
            state.accounts.size - 1
        )
        return CurrencyBalancesEffect.UpdateAccountsPosition(
            fromAccountPosition,
            wish.position,
            calculateRateUseCase(
                DEFAULT_EXCHANGE_COUNT,
                state.currentRate(fromAccountPosition),
                state.currentRate(wish.position)
            )
        )
    }

    private fun fromAccountPositionUpdate(
        state: CurrencyBalancesState,
        wish: CurrencyBalancesWish.FromAccountPositionUpdate
    ): CurrencyBalancesEffect.UpdateAccountsPosition {
        val toAccountsPosition = calculateUnequalPosition(
            wish.position,
            state.toAccountPosition,
            state.accounts.size - 1
        )
        return CurrencyBalancesEffect.UpdateAccountsPosition(
            wish.position, toAccountsPosition, calculateRateUseCase(
                DEFAULT_EXCHANGE_COUNT,
                state.currentRate(toAccountsPosition),
                state.currentRate(wish.position)
            )
        )
    }

    private fun calculateUnequalPosition(first: Int, second: Int, size: Int) =
        when {
            first == second && second + 1 in (0..size) -> second + 1
            first == second && second - 1 in (1..size) -> second - 1
            else -> second
        }

    private fun CurrencyBalancesState.currentRate(position: Int) =
        rates.getCurrentRate(accounts[position].currencyId)?.rate ?: 0.0

    private fun List<CurrencyRate>.getCurrentRate(fromAccountId: Int) =
        find { rate -> rate.currency.id == fromAccountId }
}