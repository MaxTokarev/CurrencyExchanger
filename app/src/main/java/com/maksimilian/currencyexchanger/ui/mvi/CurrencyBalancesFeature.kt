package com.maksimilian.currencyexchanger.ui.mvi

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.maksimilian.currencyexchanger.common.mapList
import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import com.maksimilian.currencyexchanger.domain.model.CurrencyRate
import com.maksimilian.currencyexchanger.domain.model.CurrencyRates
import com.maksimilian.currencyexchanger.domain.usecase.ObserveCurrencyRatesUseCase
import com.maksimilian.currencyexchanger.domain.usecase.account.CalculateRateUseCase
import com.maksimilian.currencyexchanger.domain.usecase.account.observe.ObserveUserAccountsUseCase
import com.maksimilian.currencyexchanger.ui.CurrencyAccountUi
import com.maksimilian.currencyexchanger.ui.mapper.CurrencyAccountMapperDomainToUi
import com.maksimilian.currencyexchanger.ui.mvi.CurrencyBalancesFeature.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyBalancesFeature @Inject constructor(
    bootstrapper: BootstrapperImpl
) : ActorReducerFeature<Wish, Effect, State, News>(
    initialState = State(),
    reducer = ReducerImpl(),
    actor = ActorImpl(),
    newsPublisher = NewsPublisherImpl(),
    bootstrapper = bootstrapper
) {

    sealed class Effect {
        object StartLoading : Effect()
        class LoadedData(val accounts: List<CurrencyAccountUi>, val rates: CurrencyRates) : Effect()
        class UpdateAccountsPosition(val fromAccount: Int, val toAccount: Int) : Effect()
        class UpdateAccountsCount(val fromAccount: Double, val toAccount: Double) : Effect()
        class ErrorLoading(val throwable: Throwable) : Effect()
    }

    sealed class News {
        class Error(val throwable: Throwable) : News()
    }

    data class State(
        val isLoading: Boolean = false,
        val accounts: List<CurrencyAccountUi> = emptyList(),
        val fromAccountPosition: Int = 0,
        val toAccountPosition: Int = 1,

        val fromAccountCount: Double = 0.0,
        val toAccountCount: Double = 0.0,

        val currentRate: Double = 0.0,
        val rates: List<CurrencyRate> = emptyList()
    )

    sealed class Wish {
        class FromAccountPositionUpdate(val position: Int) : Wish()
        class ToAccountPositionUpdate(val position: Int) : Wish()
        class FromAccountCountUpdate(val value: String) : Wish()
        class ToAccountCountUpdate(val value: String) : Wish()
        class Error(val throwable: Throwable) : Wish()
        class UpdateData(val accounts: List<CurrencyAccount>, val rates: CurrencyRates) : Wish()
    }

    private class NewsPublisherImpl :
        NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(wish: Wish, effect: Effect, state: State): News? = when (effect) {
            is Effect.ErrorLoading -> News.Error(effect.throwable)
            else -> null
        }
    }

    private class ActorImpl : Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> = when (wish) {
            is Wish.UpdateData -> Observable.just(wish)
                .map { CurrencyAccountMapperDomainToUi().mapList(it.accounts) }
                .observeOn(AndroidSchedulers.mainThread())
                .map { Effect.LoadedData(it, wish.rates) }
            is Wish.Error -> Observable.just(Effect.ErrorLoading(wish.throwable))
            is Wish.ToAccountPositionUpdate -> Observable.just(
                Effect.UpdateAccountsPosition(state.fromAccountPosition, wish.position)
            )
            is Wish.FromAccountPositionUpdate -> Observable.just(
                Effect.UpdateAccountsPosition(wish.position, state.toAccountPosition)
            )
            is Wish.FromAccountCountUpdate -> Observable.just(
                Effect.UpdateAccountsCount(fromAccount = wish.value.toDoubleOrNull()?: 0.0, toAccount = state.toAccountCount)
            )
            is Wish.ToAccountCountUpdate -> Observable.just(
                Effect.UpdateAccountsCount(fromAccount = state.fromAccountCount, toAccount = wish.value.toDoubleOrNull()?: 0.0)
            )
        }
    }

    private class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State =
            when (effect) {
                is Effect.ErrorLoading -> state.copy(isLoading = false)
                is Effect.LoadedData -> state.copy(
                    isLoading = false,
                    accounts = effect.accounts,
                    rates = effect.rates.rates
                )
                Effect.StartLoading -> state.copy(isLoading = true)
                is Effect.UpdateAccountsPosition -> state.copy(
                    toAccountPosition = effect.toAccount,
                    fromAccountPosition = effect.fromAccount,
                    currentRate = CalculateRateUseCase.Base(1.0)
                        .calculate(
                            state.currentRate(effect.fromAccount),
                            state.currentRate(effect.toAccount)
                        )
                )
                is Effect.UpdateAccountsCount -> state.copy(
                    fromAccountCount = effect.fromAccount,
                    toAccountCount = CalculateRateUseCase.Base(effect.fromAccount)
                        .calculate(
                            state.currentRate(state.fromAccountPosition),
                            state.currentRate(state.toAccountPosition)
                        )
                )
            }

        private fun State.currentRate(position: Int) =
            rates.getCurrentRate(accounts[position].currencyId)?.rate ?: 0.0

        private fun List<CurrencyRate>.getCurrentRate(fromAccountId: Int) =
            find { rate -> rate.currency.id == fromAccountId }
    }

    class BootstrapperImpl @Inject constructor(
        private val observeUserAccountsUseCase: ObserveUserAccountsUseCase,
        private val observeCurrencyRatesUseCase: ObserveCurrencyRatesUseCase
    ) : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> {
            return Observable.zip(
                observeUserAccountsUseCase(),
                observeCurrencyRatesUseCase()
            ) { accounts, rates -> accounts to rates }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Wish> { (accounts, rates) -> Wish.UpdateData(accounts, rates) }
                .onErrorReturn { Wish.Error(it) }
        }
    }
}