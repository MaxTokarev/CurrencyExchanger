package com.maksimilian.currencyexchanger.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.maksimilian.currencyexchanger.common.dao.BaseDao
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRatesEntity
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRatesEntityJoin
import io.reactivex.Observable

@Dao
interface CurrencyRatesDao : BaseDao<CurrencyRatesEntity> {

    @Transaction
    @Query("SELECT * FROM ${CurrencyRatesEntity.TABLE_NAME}")
    fun observe(): Observable<CurrencyRatesEntityJoin>
}