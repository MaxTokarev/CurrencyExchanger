package com.maksimilian.currencyexchanger.ui.mvi

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.maksimilian.currencyexchanger.ui.screens.converter.mvi.ConverterNews
import com.maksimilian.currencyexchanger.ui.screens.converter.mvi.ConverterWish
import io.reactivex.Observable
import javax.inject.Inject

class CurrencyReducerFeature @Inject constructor(

) : ActorReducerFeature<ConverterWish, ConverterEffect, ConverterState, ConverterNews>(
    initialState = ConverterState(),
    reducer = ConverterReducer(),
    actor = ConverterActor(),
    newsPublisher = ConverterNewsPublisher()
) {

    class ConverterNewsPublisher :
        NewsPublisher<ConverterWish, ConverterEffect, ConverterState, ConverterNews> {
        override fun invoke(
            action: ConverterWish,
            effect: ConverterEffect,
            state: ConverterState
        ): ConverterNews? {
            return null
        }
    }

    class ConverterActor : Actor<ConverterState, ConverterWish, ConverterEffect> {
        override fun invoke(
            state: ConverterState,
            action: ConverterWish
        ): Observable<out ConverterEffect> {
            return Observable.just(1).map { ConverterEffect.Empty }
        }
    }

    class ConverterReducer : Reducer<ConverterState, ConverterEffect> {
        override fun invoke(state: ConverterState, effect: ConverterEffect): ConverterState {
            return ConverterState()
        }
    }
}