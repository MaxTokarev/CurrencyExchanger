package com.maksimilian.currencyexchanger.data.local.source

import com.maksimilian.currencyexchanger.common.dao.BaseDao.Companion.upInsert
import com.maksimilian.currencyexchanger.common.mapList
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyDao
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyRateDao
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyRatesDao
import com.maksimilian.currencyexchanger.data.mappers.*
import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.data.network.model.CurrencyRatesData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CurrencyLocalDataSourceImpl @Inject constructor(
    private val currencyRateDao: CurrencyRateDao,
    private val currencyRatesDao: CurrencyRatesDao,
    private val currencyDao: CurrencyDao,
    private val currencyMapperEntityToData: CurrencyMapperEntityToData,
    private val currencyMapperDataToEntity: CurrencyMapperDataToEntity,
    private val currencyRatesMapperEntityToData: CurrencyRatesMapperEntityToData,
    private val currencyRatesMapperDataToEntity: CurrencyRatesMapperDataToEntity,
    private val currencyRateMapperDataToEntity: CurrencyRateMapperDataToEntity,
) : CurrencyLocalDataSource {

    override fun getAllCurrencies(): Single<List<CurrencyData>> {
        return currencyDao.getAll().map { currencyMapperEntityToData.mapList(it) }
    }

    override fun upInsertCurrencies(currencies: List<CurrencyData>): Completable =
        Completable.fromAction {
            currencyDao.upInsert(currencies.map { currencyMapperDataToEntity.map(it) })
        }

    override fun upInsertCurrencyRates(currencyRatesData: CurrencyRatesData): Completable =
        Completable.fromAction {
            currencyRatesDao.upInsert(currencyRatesMapperDataToEntity.map(currencyRatesData))
            currencyRateDao.upInsert(currencyRateMapperDataToEntity.mapList(currencyRatesData.rates))
        }


    override fun observeCurrencyRates(): Observable<CurrencyRatesData> =
        currencyRateDao.observe()
            .flatMap { currenciesRate ->
                currencyRatesDao.observe()
                    .map { currencyRatesMapperEntityToData.map(currenciesRate to it) }
            }
}

