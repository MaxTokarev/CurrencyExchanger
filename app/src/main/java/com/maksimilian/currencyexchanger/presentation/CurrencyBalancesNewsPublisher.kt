package com.maksimilian.currencyexchanger.presentation

import com.badoo.mvicore.element.NewsPublisher
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesEffect
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesNews
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesState
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesWish

class CurrencyBalancesNewsPublisher :
    NewsPublisher<CurrencyBalancesWish, CurrencyBalancesEffect, CurrencyBalancesState, CurrencyBalancesNews> {
    override fun invoke(
        wish: CurrencyBalancesWish,
        effect: CurrencyBalancesEffect,
        state: CurrencyBalancesState
    ): CurrencyBalancesNews? = when (effect) {
        is CurrencyBalancesEffect.ErrorLoading -> CurrencyBalancesNews.Error(effect.throwable)
        is CurrencyBalancesEffect.SuccessExchange -> CurrencyBalancesNews.SuccessExchange
        else -> null
    }
}