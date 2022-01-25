package com.maksimilian.currencyexchanger.ui

import com.badoo.binder.named
import com.badoo.binder.using
import com.badoo.mvicore.android.AndroidBindings
import com.maksimilian.currencyexchanger.common.extensions.combineLatest
import com.maksimilian.currencyexchanger.ui.mvi.*

class MainActivityBindings(
    view: MainActivity,
    private val currencyFeature: CurrencyBalancesFeature,
    private val exchangeFeature: ExchangeFeature,
    private val newsListener: NewsListener
) : AndroidBindings<MainActivity>(view) {

    override fun setup(view: MainActivity) {
        binder.bind(
            combineLatest(
                currencyFeature,
                exchangeFeature
            ) to view using ViewModelTransformer()
        )
        binder.bind(view to currencyFeature using UiEventTransformer())
        binder.bind(view to exchangeFeature using UiEventTransformer2())
        binder.bind(currencyFeature.news to newsListener named "MainActivity.News")
    }
}