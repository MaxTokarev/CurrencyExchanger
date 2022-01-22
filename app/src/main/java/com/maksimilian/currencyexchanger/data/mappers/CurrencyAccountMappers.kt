package com.maksimilian.currencyexchanger.data.mappers

import com.maksimilian.currencyexchanger.common.Mapper
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyAccountEntity
import com.maksimilian.currencyexchanger.data.models.CurrencyAccountData
import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import javax.inject.Inject

class CurrencyAccountMapperLocalToData @Inject constructor() :
    Mapper<CurrencyAccountEntity, CurrencyAccountData> {
    override fun map(data: CurrencyAccountEntity): CurrencyAccountData =
        CurrencyAccountData(
            id = data.id,
            name = data.name,
            balance = data.balance,
            symbol = data.symbol
        )
}

class CurrencyAccountMapperDataToLocal @Inject constructor() :
    Mapper<CurrencyAccountData, CurrencyAccountEntity> {
    override fun map(data: CurrencyAccountData): CurrencyAccountEntity =
        CurrencyAccountEntity(
            balance = data.balance,
            name = data.name,
            symbol = data.symbol
        )
}

class CurrencyAccountMappersDataToDomain @Inject constructor() :
    Mapper<CurrencyAccountData, CurrencyAccount> {
    override fun map(data: CurrencyAccountData): CurrencyAccount = CurrencyAccount(
        id = data.id,
        name = data.name,
        balance = data.balance,
        symbol = data.symbol
    )
}