package com.maksimilian.currencyexchanger.ui.mvi

import androidx.annotation.StringRes
import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.maksimilian.currencyexchanger.domain.model.CurrencyRate
import com.maksimilian.currencyexchanger.domain.model.CurrencyRates
import com.maksimilian.currencyexchanger.domain.usecase.ObserveCurrencyRatesUseCase
import com.maksimilian.currencyexchanger.ui.mvi.ExchangeFeature.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExchangeFeature @Inject constructor(
    bootstrapper: BootstrapperImpl
) : ActorReducerFeature<Wish, Effect, State, News>(
    initialState = State(),
    newsPublisher = null,
    actor = ActorImpl(),
    reducer = ReducerImpl(),
    bootstrapper = bootstrapper
) {

    data class State(
        val isLoading: Boolean = false,
        val currencyRate: Double = 0.0,
        val id: Int = 0
    )

    sealed class News {
        object ExchangeSuccessful : News()
        object Error : News()
    }

    sealed class Wish {
        object ExchangeCurrencies : Wish()
        class RatesUpdated(val rates: CurrencyRates) : Wish()
        class CurrentCurrency(val id: Int) : Wish()
    }

    sealed class Effect {
        object StartLoading : Effect()
        class ValidationError(@StringRes val cause: Int) : Effect()
        object SuccessExchange : Effect()
        class CurrentCurrency(val id: Int) : Effect()

        class RateUpdate(val rate: CurrencyRate) : Effect()
    }

    class ActorImpl @Inject constructor(

    ) : Actor<State, Wish, Effect> {
        override fun invoke(state: State, action: Wish): Observable<out Effect> {
            return when (action) {
                is Wish.ExchangeCurrencies -> Observable.just(Effect.StartLoading)
//                    .map {
//                        when (val validateResult = ExchangeValidator.validate(action.count)) {
//                            is ExchangeValidator.Result.InValid -> Effect.ValidationError(
//                                validateResult.causeRes
//                            )
//                            ExchangeValidator.Result.Valid ->
//                        }
//                    }
                is Wish.RatesUpdated -> Observable.just(
                    Effect.RateUpdate(action.rates.rates.find { it.currency.id == state.id }!!)
                )
                is Wish.CurrentCurrency -> Observable.just(Effect.CurrentCurrency(action.id))
            }
        }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State =
            when (effect) {
                Effect.StartLoading -> state.copy(isLoading = true)
                Effect.SuccessExchange -> state.copy(isLoading = false)
                is Effect.ValidationError -> state.copy(isLoading = false)
                is Effect.RateUpdate -> state.copy(
                    isLoading = false,
                    currencyRate = effect.rate.rate
                )
                is Effect.CurrentCurrency -> state.copy(
                    isLoading = false,
                    id = effect.id
                )
            }
    }

    class BootstrapperImpl @Inject constructor(
        private val observeRates: ObserveCurrencyRatesUseCase
    ) : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> {
//            return observeRates()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map { Wish.RatesUpdated(it) }
            return Observable.empty()
        }
    }

    class NewsImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(action: Wish, effect: Effect, state: State): News? {
            return when (effect) {
                else -> null
            }
        }
    }
}