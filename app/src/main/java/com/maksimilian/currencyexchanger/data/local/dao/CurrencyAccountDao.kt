package com.maksimilian.currencyexchanger.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.maksimilian.currencyexchanger.common.dao.BaseDao
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyAccountEntity
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyAccountEntityJoin
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CurrencyAccountDao : BaseDao<CurrencyAccountEntity> {
    @Transaction
    @Query("SELECT * FROM ${CurrencyAccountEntity.TABLE_NAME}")
    fun getAllAccounts(): Single<List<CurrencyAccountEntityJoin>>

    @Transaction
    @Query("SELECT * FROM ${CurrencyAccountEntity.TABLE_NAME}")
    fun observeAllAccounts(): Observable<List<CurrencyAccountEntityJoin>>

    @Query("UPDATE ${CurrencyAccountEntity.TABLE_NAME} SET balance = :balance WHERE id = :accountId")
    fun updateBalance(accountId: Int, balance: Double): Completable
}