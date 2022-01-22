package com.maksimilian.currencyexchanger.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.maksimilian.currencyexchanger.common.dao.BaseDao
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyAccountEntity
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CurrencyAccountDao : BaseDao<CurrencyAccountEntity> {
    @Query("SELECT * FROM ${CurrencyAccountEntity.TABLE_NAME}")
    fun getAllAccounts(): Single<List<CurrencyAccountEntity>>

    @Query("SELECT * FROM ${CurrencyAccountEntity.TABLE_NAME}")
    fun observeAllAccounts(): Observable<List<CurrencyAccountEntity>>
}