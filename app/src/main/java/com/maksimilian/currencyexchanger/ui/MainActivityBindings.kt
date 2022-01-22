package com.maksimilian.currencyexchanger.ui

import com.badoo.binder.named
import com.badoo.binder.using
import com.badoo.mvicore.android.AndroidBindings
import com.maksimilian.currencyexchanger.ui.mvi.CurrencyBalancesFeature
import com.maksimilian.currencyexchanger.ui.mvi.NewsListener
import com.maksimilian.currencyexchanger.ui.mvi.UiEventTransformer
import com.maksimilian.currencyexchanger.ui.mvi.ViewModelTransformer

class MainActivityBindings(
    view: MainActivity,
    private val currencyReducerFeature: CurrencyBalancesFeature,
    private val newsListener: NewsListener
) : AndroidBindings<MainActivity>(view) {

    override fun setup(view: MainActivity) {
        binder.bind(view to currencyReducerFeature using UiEventTransformer())
        binder.bind(currencyReducerFeature to view using ViewModelTransformer())
        binder.bind(currencyReducerFeature.news to newsListener named "MainActivity.News")
    }
}