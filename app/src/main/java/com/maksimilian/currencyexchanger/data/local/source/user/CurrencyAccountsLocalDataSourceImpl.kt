package com.maksimilian.currencyexchanger.data.local.source.user

import com.maksimilian.currencyexchanger.common.dao.BaseDao.Companion.upInsert
import com.maksimilian.currencyexchanger.common.mapList
import com.maksimilian.currencyexchanger.data.local.dao.CurrencyAccountDao
import com.maksimilian.currencyexchanger.data.mappers.CurrencyAccountMapperDataToLocal
import com.maksimilian.currencyexchanger.data.mappers.CurrencyAccountMapperLocalToData
import com.maksimilian.currencyexchanger.data.models.CurrencyAccountData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CurrencyAccountsLocalDataSourceImpl @Inject constructor(
    private val currencyAccountDao: CurrencyAccountDao,
    private val mapperLocalToData: CurrencyAccountMapperLocalToData,
    private val mapperDataToLocal: CurrencyAccountMapperDataToLocal
) : CurrencyAccountsLocalDataSource {
    override fun getAllAccounts(): Single<List<CurrencyAccountData>> =
        currencyAccountDao.getAllAccounts().map { mapperLocalToData.mapList(it) }

    override fun upInsertAccounts(accounts: List<CurrencyAccountData>): Completable =
        Completable.fromAction { currencyAccountDao.upInsert(mapperDataToLocal.mapList(accounts)) }

    override fun observeAccounts(): Observable<List<CurrencyAccountData>> =
        currencyAccountDao.observeAllAccounts().map { mapperLocalToData.mapList(it) }

    override fun updateBalance(accountId: Int, newBalance: Double): Completable =
         currencyAccountDao.updateBalance(accountId, newBalance)
}