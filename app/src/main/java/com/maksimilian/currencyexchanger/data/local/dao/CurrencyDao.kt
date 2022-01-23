package com.maksimilian.currencyexchanger.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.maksimilian.currencyexchanger.common.dao.BaseDao
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyEntity
import com.maksimilian.currencyexchanger.domain.model.Currency
import io.reactivex.Single

@Dao
interface CurrencyDao : BaseDao<CurrencyEntity> {

    @Query("SELECT * FROM ${CurrencyEntity.TABLE_NAME}")
    fun getAll(): Single<List<CurrencyEntity>>
}