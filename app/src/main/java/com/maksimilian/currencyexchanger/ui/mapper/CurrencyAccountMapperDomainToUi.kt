package com.maksimilian.currencyexchanger.ui.mapper

import com.maksimilian.currencyexchanger.common.Mapper
import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import com.maksimilian.currencyexchanger.ui.CurrencyAccountUi

class CurrencyAccountMapperDomainToUi : Mapper<CurrencyAccount, CurrencyAccountUi> {
    override fun map(data: CurrencyAccount): CurrencyAccountUi {
        return CurrencyAccountUi(
            id = data.id,
            name = data.name + data.symbol,
            balance = data.balance.toString()
        )
    }
}