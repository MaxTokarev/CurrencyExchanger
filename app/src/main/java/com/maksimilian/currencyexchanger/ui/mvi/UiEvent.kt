package com.maksimilian.currencyexchanger.ui.mvi

import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesWish

sealed class UiEvent {
    class FromAccountScrolledTo(val position: Int) : UiEvent()
    class ToAccountScrolledTo(val position: Int) : UiEvent()
    object ExchangeClicked : UiEvent()
    class FromAccountTextEntered(val text: String) : UiEvent()
    class ToAccountTextEntered(val text: String) : UiEvent()
    class CurrentCurrency(val id: Int) : UiEvent()
}

class UiEventTransformer : (UiEvent) -> CurrencyBalancesWish? {
    override fun invoke(event: UiEvent): CurrencyBalancesWish? = when (event) {
        is UiEvent.FromAccountScrolledTo ->
            CurrencyBalancesWish.FromAccountPositionUpdate(event.position)
        is UiEvent.ToAccountScrolledTo ->
            CurrencyBalancesWish.ToAccountPositionUpdate(event.position)
        is UiEvent.FromAccountTextEntered ->
            CurrencyBalancesWish.FromAccountCountUpdate(event.text)
        is UiEvent.ToAccountTextEntered ->
            CurrencyBalancesWish.ToAccountCountUpdate(event.text)
        is UiEvent.ExchangeClicked -> CurrencyBalancesWish.Exchange
        else -> null
    }
}

class UiEventTransformer2 : (UiEvent) -> ExchangeFeature.Wish? {
    override fun invoke(event: UiEvent): ExchangeFeature.Wish? =
        when (event) {
            is UiEvent.ExchangeClicked -> ExchangeFeature.Wish.ExchangeCurrencies
            is UiEvent.CurrentCurrency -> ExchangeFeature.Wish.CurrentCurrency(event.id)
            else -> null
        }
}