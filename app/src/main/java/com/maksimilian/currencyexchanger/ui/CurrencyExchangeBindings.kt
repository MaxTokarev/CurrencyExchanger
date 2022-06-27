package com.maksimilian.currencyexchanger.ui

import androidx.lifecycle.LifecycleOwner
import com.badoo.binder.using
import com.badoo.mvicore.android.AndroidBindings
import com.maksimilian.currencyexchanger.common.extensions.combineLatest
import com.maksimilian.currencyexchanger.presentation.CurrencyBalancesFeature
import com.maksimilian.currencyexchanger.ui.model.ViewModelTransformer
import com.maksimilian.currencyexchanger.ui.mvi.ExchangeFeature
import com.maksimilian.currencyexchanger.ui.mvi.NewsListener
import com.maksimilian.currencyexchanger.ui.mvi.UiEventTransformer
import com.maksimilian.currencyexchanger.ui.mvi.UiEventTransformer2

class CurrencyExchangeBindings(
    lifecycleOwner: LifecycleOwner,
    private val currencyFeature: CurrencyBalancesFeature,
    private val exchangeFeature: ExchangeFeature,
    private val newsListener: NewsListener
) : AndroidBindings<CurrencyExchangeFragment>(lifecycleOwner) {

    override fun setup(view: CurrencyExchangeFragment) {
        binder.bind(
            combineLatest(
                currencyFeature,
                exchangeFeature
            ) to view using ViewModelTransformer()
        )
        binder.bind(view to currencyFeature using UiEventTransformer())
        binder.bind(view to exchangeFeature using UiEventTransformer2())
        binder.bind(currencyFeature.news to newsListener)
    }
}