package com.maksimilian.currencyexchanger.data.mappers

import com.maksimilian.currencyexchanger.common.Mapper
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyAccountEntity
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyAccountEntityJoin
import com.maksimilian.currencyexchanger.data.models.CurrencyAccountData
import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import javax.inject.Inject

class CurrencyAccountMapperLocalToData @Inject constructor(
    private val currencyMapper: CurrencyMapperEntityToData
) : Mapper<CurrencyAccountEntityJoin, CurrencyAccountData> {
    override fun map(data: CurrencyAccountEntityJoin): CurrencyAccountData {
        val account = data.account
        return CurrencyAccountData(
            id = account.id,
            currency = currencyMapper.map(data.currency),
            balance = account.balance
        )
    }
}

class CurrencyAccountMapperDataToLocal @Inject constructor(
) : Mapper<CurrencyAccountData, CurrencyAccountEntity> {
    override fun map(data: CurrencyAccountData): CurrencyAccountEntity =
        CurrencyAccountEntity(
            id = data.id,
            balance = data.balance,
            currencyId = data.currency.id
        )
}

class CurrencyAccountMappersDataToDomain @Inject constructor(
    private val currencyMapper: CurrencyMapperDataToDomain
) : Mapper<CurrencyAccountData, CurrencyAccount> {
    override fun map(data: CurrencyAccountData): CurrencyAccount = CurrencyAccount(
        id = data.id,
        balance = data.balance,
        currency = currencyMapper.map(data.currency)
    )
}