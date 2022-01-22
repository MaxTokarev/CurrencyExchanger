package com.maksimilian.currencyexchanger.ui.mvi

sealed class UiEvent {
    class FromAccountScrolledTo(val position: Int) : UiEvent()
    class ToAccountScrolledTo(val position: Int) : UiEvent()
    object ExchangeClicked : UiEvent()
}

class UiEventTransformer : (UiEvent) -> CurrencyBalancesFeature.Wish? {
    override fun invoke(event: UiEvent): CurrencyBalancesFeature.Wish? = when (event) {
        is UiEvent.FromAccountScrolledTo ->
            CurrencyBalancesFeature.Wish.FromAccountUpdate(event.position)
        is UiEvent.ToAccountScrolledTo ->
            CurrencyBalancesFeature.Wish.ToAccountUpdate(event.position)
        else -> null
    }
}