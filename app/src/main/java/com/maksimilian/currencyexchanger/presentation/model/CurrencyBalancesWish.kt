package com.maksimilian.currencyexchanger.presentation.model

import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import com.maksimilian.currencyexchanger.domain.model.CurrencyRates

sealed class CurrencyBalancesWish {
    class FromAccountPositionUpdate(val position: Int) : CurrencyBalancesWish()
    class ToAccountPositionUpdate(val position: Int) : CurrencyBalancesWish()
    class FromAccountCountUpdate(val value: String) : CurrencyBalancesWish()
    class ToAccountCountUpdate(val value: String) : CurrencyBalancesWish()
    class Error(val throwable: Throwable) : CurrencyBalancesWish()
    class UpdateData(val accounts: List<CurrencyAccount>, val rates: CurrencyRates) : CurrencyBalancesWish()
    object Exchange : CurrencyBalancesWish()
}