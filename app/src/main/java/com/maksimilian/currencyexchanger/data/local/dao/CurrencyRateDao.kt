package com.maksimilian.currencyexchanger.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.maksimilian.currencyexchanger.common.dao.BaseDao
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRateEntity
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRateEntityJoin
import io.reactivex.Observable

@Dao
interface CurrencyRateDao : BaseDao<CurrencyRateEntity> {

    @Transaction
    @Query("SELECT * FROM ${CurrencyRateEntity.TABLE_NAME}")
    fun observe(): Observable<List<CurrencyRateEntityJoin>>
}