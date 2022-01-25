package com.maksimilian.currencyexchanger.ui.mapper

import com.maksimilian.currencyexchanger.common.Mapper
import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import com.maksimilian.currencyexchanger.ui.CurrencyAccountUi

class CurrencyAccountMapperDomainToUi : Mapper<CurrencyAccount, CurrencyAccountUi> {
    override fun map(data: CurrencyAccount): CurrencyAccountUi {
        return CurrencyAccountUi(
            id = data.id,
            currencyId = data.currency.id,
            name = data.currency.name,
            balance = data.balance.toString(),
            symbol = data.currency.symbol,
            shortName = data.currency.shortName
        )
    }
}