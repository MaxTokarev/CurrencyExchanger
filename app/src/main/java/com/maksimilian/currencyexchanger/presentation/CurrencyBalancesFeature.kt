package com.maksimilian.currencyexchanger.presentation

import com.badoo.mvicore.feature.ActorReducerFeature
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesEffect
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesNews
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesState
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesWish
import javax.inject.Inject


class CurrencyBalancesFeature @Inject constructor(
    bootstrapper: CurrencyBalancesBootstrapper,
    actor: CurrencyBalancesEffectActor,
) : ActorReducerFeature<CurrencyBalancesWish, CurrencyBalancesEffect, CurrencyBalancesState, CurrencyBalancesNews>(
    initialState = CurrencyBalancesState(),
    reducer = CurrencyBalancesReducer(),
    actor = actor,
    newsPublisher = CurrencyBalancesNewsPublisher(),
    bootstrapper = bootstrapper
)