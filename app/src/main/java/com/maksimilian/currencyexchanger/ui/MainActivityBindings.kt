package com.maksimilian.currencyexchanger.ui

import com.badoo.binder.named
import com.badoo.binder.using
import com.badoo.mvicore.android.AndroidBindings
import com.maksimilian.currencyexchanger.ui.mvi.CurrencyReducerFeature
import com.maksimilian.currencyexchanger.ui.mvi.NewsListener
import javax.inject.Inject

class MainActivityBindings(
    view: MainActivity,
    private val currencyReducerFeature: CurrencyReducerFeature,
    private val newsListener: NewsListener
) : AndroidBindings<MainActivity>(view) {

    override fun setup(view: MainActivity) {
        binder.bind(view to currencyReducerFeature)
        binder.bind(currencyReducerFeature to view)
        binder.bind(currencyReducerFeature.news to newsListener named "MainActivity.News")
    }
}