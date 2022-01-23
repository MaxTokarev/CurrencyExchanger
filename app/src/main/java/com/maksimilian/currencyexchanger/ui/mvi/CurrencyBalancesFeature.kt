package com.maksimilian.currencyexchanger.ui.mvi

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.maksimilian.currencyexchanger.common.mapList
import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import com.maksimilian.currencyexchanger.domain.usecase.account.fetch.FetchIfRequireUserAccountsUseCase
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
        class LoadedAccounts(val accounts: List<CurrencyAccountUi>) : Effect()
        class UpdateAccountsPosition(val fromAccount: Int, val toAccount: Int) : Effect()
        class ErrorLoading(val throwable: Throwable) : Effect()
    }

    sealed class News {
        class Error(val throwable: Throwable) : News()
    }

    data class State(
        val isLoading: Boolean = false,

        val fromAccounts: List<CurrencyAccountUi> = emptyList(),
        val toAccounts: List<CurrencyAccountUi> = emptyList(),
        val accounts: List<CurrencyAccountUi> = emptyList(),
        val fromAccountPosition: Int = 0,
        val toAccountPosition: Int = 1,
    )

    sealed class Wish {
        class FromAccountUpdate(val position: Int) : Wish()
        class ToAccountUpdate(val position: Int) : Wish()
        class UpdatedUserAccounts(val accounts: List<CurrencyAccount>) : Wish()
        class Error(val throwable: Throwable) : Wish()
    }

    private class NewsPublisherImpl :
        NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(
            wish: Wish,
            effect: Effect,
            state: State
        ): News? = when (effect) {
            is Effect.ErrorLoading -> News.Error(effect.throwable)
            else -> null
        }
    }

    private class ActorImpl : Actor<State, Wish, Effect> {
        override fun invoke(
            state: State,
            wish: Wish
        ): Observable<out Effect> = when (wish) {
            is Wish.UpdatedUserAccounts -> Observable.just(wish.accounts)
                .map { CurrencyAccountMapperDomainToUi().mapList(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .map { Effect.LoadedAccounts(it) }
            is Wish.FromAccountUpdate -> {
                val fromPosition = calculatePosition(state.toAccountPosition, wish.position)
                Observable.just(
                    Effect.UpdateAccountsPosition(fromPosition, state.toAccountPosition)
                )
            }
            is Wish.ToAccountUpdate -> {
                val toPosition = calculatePosition(state.fromAccountPosition, wish.position)
                Observable.just(
                    Effect.UpdateAccountsPosition(state.fromAccountPosition, toPosition)
                )
            }
            is Wish.Error -> Observable.just(Effect.ErrorLoading(wish.throwable))
        }

        private fun calculatePosition(oppositePosition: Int, newPosition: Int) =
            if (oppositePosition == newPosition) newPosition + 1 else newPosition
    }

    private class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State =
            when (effect) {
                is Effect.ErrorLoading -> state.copy(isLoading = false)
                is Effect.LoadedAccounts -> state.copy(
                    isLoading = false,
                    accounts = effect.accounts,
                    fromAccounts = effect.accounts.remove(state.toAccountPosition),
                    toAccounts = effect.accounts.remove(state.fromAccountPosition)
                )
                Effect.StartLoading -> state.copy(isLoading = true)
                is Effect.UpdateAccountsPosition -> state.copy(
                    toAccountPosition = effect.toAccount,
                    fromAccountPosition = effect.fromAccount,
                    fromAccounts = state.accounts.remove(effect.toAccount),
                    toAccounts = state.accounts.remove(effect.fromAccount)
                )
            }

        private fun List<CurrencyAccountUi>.remove(position: Int): List<CurrencyAccountUi> =
            toMutableList().apply { removeAt(position) }
    }

    class BootstrapperImpl @Inject constructor(
        private val observeUserAccountsUseCase: ObserveUserAccountsUseCase,
        private val fetchIfRequireUserAccountsUseCase: FetchIfRequireUserAccountsUseCase
    ) : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> {
            return fetchIfRequireUserAccountsUseCase()
                .andThen(observeUserAccountsUseCase())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Wish> { Wish.UpdatedUserAccounts(it) }
                .onErrorReturn { Wish.Error(it) }
        }
    }
}