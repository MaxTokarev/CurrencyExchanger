package com.maksimilian.currencyexchanger.ui.mvi

sealed class UiEvent {
    class FromAccountScrolledTo(val position: Int) : UiEvent()
    class ToAccountScrolledTo(val position: Int) : UiEvent()
    object ExchangeClicked: UiEvent()
    class FromAccountTextEntered(val text: String) : UiEvent()
    class ToAccountTextEntered(val text: String) : UiEvent()
    class CurrentCurrency(val id: Int) : UiEvent()
}

class UiEventTransformer : (UiEvent) -> CurrencyBalancesFeature.Wish? {
    override fun invoke(event: UiEvent): CurrencyBalancesFeature.Wish? = when (event) {
        is UiEvent.FromAccountScrolledTo ->
            CurrencyBalancesFeature.Wish.FromAccountPositionUpdate(event.position)
        is UiEvent.ToAccountScrolledTo ->
            CurrencyBalancesFeature.Wish.ToAccountPositionUpdate(event.position)
        is UiEvent.FromAccountTextEntered ->
            CurrencyBalancesFeature.Wish.FromAccountCountUpdate(event.text)
        is UiEvent.ToAccountTextEntered ->
            CurrencyBalancesFeature.Wish.ToAccountCountUpdate(event.text)
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