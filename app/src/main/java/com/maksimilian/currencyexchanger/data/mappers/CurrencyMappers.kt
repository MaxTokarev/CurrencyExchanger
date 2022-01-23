package com.maksimilian.currencyexchanger.data.mappers

import com.maksimilian.currencyexchanger.common.Mapper
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyEntity
import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.domain.model.Currency
import javax.inject.Inject

class CurrencyMapperEntityToData @Inject constructor() : Mapper<CurrencyEntity, CurrencyData> {
    override fun map(data: CurrencyEntity): CurrencyData = CurrencyData(
        id = data.id,
        name = data.name,
        symbol = data.symbol,
        shortName = data.shortName
    )
}

class CurrencyMapperDataToEntity @Inject constructor() : Mapper<CurrencyData, CurrencyEntity> {
    override fun map(data: CurrencyData): CurrencyEntity = CurrencyEntity(
        id = data.id,
        name = data.name,
        symbol = data.symbol,
        shortName = data.shortName
    )
}

class CurrencyMapperDataToDomain @Inject constructor() : Mapper<CurrencyData, Currency> {
    override fun map(data: CurrencyData): Currency = Currency(
        id = data.id,
        name = data.name,
        symbol = data.symbol,
        shortName = data.shortName
    )
}

class CurrencyMapperApiToData @Inject constructor() :
    Mapper<Pair<Int, java.util.Currency>, CurrencyData> {
    override fun map(data: Pair<Int, java.util.Currency>): CurrencyData = CurrencyData(
        id = data.first,
        name = data.second.displayName,
        symbol = data.second.symbol,
        shortName = data.second.currencyCode
    )
}