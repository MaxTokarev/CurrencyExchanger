package com.maksimilian.currencyexchanger.ui

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.maksimilian.currencyexchanger.R
import com.maksimilian.currencyexchanger.common.ui.ObservableSourceFragment
import com.maksimilian.currencyexchanger.databinding.FragmentCurrencyExchangeBinding
import com.maksimilian.currencyexchanger.di.injector
import com.maksimilian.currencyexchanger.presentation.CurrencyBalancesFeature
import com.maksimilian.currencyexchanger.ui.model.CurrencyExchangeViewModel
import com.maksimilian.currencyexchanger.ui.mvi.ExchangeFeature
import com.maksimilian.currencyexchanger.ui.mvi.NewsListener
import com.maksimilian.currencyexchanger.ui.mvi.UiEvent
import io.reactivex.functions.Consumer
import javax.inject.Inject

class CurrencyExchangeFragment :
    ObservableSourceFragment<UiEvent>(R.layout.fragment_currency_exchange),
    Consumer<CurrencyExchangeViewModel> {

    private val binding by viewBinding(FragmentCurrencyExchangeBinding::bind)
    private lateinit var uiFacade: CurrencyExchangeUiFacade

    @Inject
    lateinit var currencyFeature: CurrencyBalancesFeature

    @Inject
    lateinit var exchangeFeature: ExchangeFeature

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CurrencyExchangeBindings(
            viewLifecycleOwner,
            currencyFeature,
            exchangeFeature,
            NewsListener(requireContext())
        ).setup(this)
        uiFacade = CurrencyExchangeUiFacade(binding, ::onNext)
    }

    override fun accept(vm: CurrencyExchangeViewModel) {
        uiFacade.accept(vm)
    }
}