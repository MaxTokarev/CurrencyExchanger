package com.maksimilian.currencyexchanger.data.mappers

import com.maksimilian.currencyexchanger.common.Mapper
import com.maksimilian.currencyexchanger.common.mapList
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRateEntity
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRateEntityJoin
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRatesEntity
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRatesEntityJoin
import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.data.network.model.CurrencyRateApiResponse
import com.maksimilian.currencyexchanger.data.network.model.CurrencyRateData
import com.maksimilian.currencyexchanger.data.network.model.CurrencyRatesData
import com.maksimilian.currencyexchanger.data.network.source.account.AccountCurrency
import com.maksimilian.currencyexchanger.data.network.source.account.AccountCurrency.*
import com.maksimilian.currencyexchanger.data.network.source.account.AccountCurrency.Companion.id
import com.maksimilian.currencyexchanger.domain.model.CurrencyRate
import com.maksimilian.currencyexchanger.domain.model.CurrencyRates
import kotlinx.datetime.Instant
import javax.inject.Inject

class CurrencyRatesMapperApiToData @Inject constructor() :
    Mapper<Pair<List<CurrencyData>, CurrencyRateApiResponse>, CurrencyRatesData> {
    override fun map(data: Pair<List<CurrencyData>, CurrencyRateApiResponse>): CurrencyRatesData {
        val (currencies, rates) = data
        return CurrencyRatesData(
            lastSyncTimestamp = rates.timestamp,
            base = currencies.findOrThrow(AccountCurrency.getIdByCode(rates.base)),
            rates = with(rates.rates) {
                listOf(euro to EURO.id, gbp to POUND.id, rub to RUB.id, usd to US.id)
                    .map { (rate, currencyId) -> rate to currencies.findOrThrow(currencyId) }
                    .map { (rate, currency) -> CurrencyRateData(currency = currency, rate = rate) }
            }
        )
    }

    private fun List<CurrencyData>.findOrThrow(id: Int): CurrencyData =
        find { currency -> currency.id == id }
            ?: error("Cannot find currency by given id - $id")
}

class CurrencyRatesMapperEntityToData @Inject constructor(
    private val currencyMapper: CurrencyMapperEntityToData,
    private val currencyRateMapper: CurrencyRateMapper
) : Mapper<Pair<List<CurrencyRateEntityJoin>, CurrencyRatesEntityJoin>, CurrencyRatesData> {
    override fun map(data: Pair<List<CurrencyRateEntityJoin>, CurrencyRatesEntityJoin>): CurrencyRatesData {
        val (currencies, rates) = data
        return CurrencyRatesData(
            lastSyncTimestamp = rates.rates.lastSyncTimestamp,
            base = currencyMapper.map(rates.baseCurrency),
            rates = currencies.map { currencyRateMapper.map(it) }
        )
    }
}

class CurrencyRatesMapperDataToEntity @Inject constructor(
) : Mapper<CurrencyRatesData, CurrencyRatesEntity> {
    override fun map(data: CurrencyRatesData): CurrencyRatesEntity {
        return CurrencyRatesEntity(
            lastSyncTimestamp = data.lastSyncTimestamp,
            baseCurrencyId = data.base.id
        )
    }
}

class CurrencyRateMapperDataToEntity @Inject constructor(
) : Mapper<CurrencyRateData, CurrencyRateEntity> {
    override fun map(data: CurrencyRateData): CurrencyRateEntity {
        return CurrencyRateEntity(
            currencyId = data.currency.id,
            rate = data.rate
        )
    }
}

class CurrencyRateMapper @Inject constructor(
    private val currencyMapper: CurrencyMapperEntityToData
) : Mapper<CurrencyRateEntityJoin, CurrencyRateData> {
    override fun map(data: CurrencyRateEntityJoin): CurrencyRateData {
        return CurrencyRateData(currency = currencyMapper.map(data.currency), rate = data.rate.rate)
    }
}

class CurrencyRatesMapperDataToDomain @Inject constructor(
    private val currencyMapper: CurrencyMapperDataToDomain,
    private val currencyRateMapper: CurrencyRateMapperDataToDomain
) : Mapper<CurrencyRatesData, CurrencyRates> {
    override fun map(data: CurrencyRatesData): CurrencyRates {
        return CurrencyRates(
            lastSyncTime = Instant.fromEpochMilliseconds(data.lastSyncTimestamp),
            baseCurrency = currencyMapper.map(data.base),
            rates = currencyRateMapper.mapList(data.rates)
        )
    }
}

class CurrencyRateMapperDataToDomain @Inject constructor(
    private val currencyMapper: CurrencyMapperDataToDomain
) : Mapper<CurrencyRateData, CurrencyRate> {
    override fun map(data: CurrencyRateData): CurrencyRate {
        return CurrencyRate(
            currency = currencyMapper.map(data.currency), rate = data.rate
        )
    }
}