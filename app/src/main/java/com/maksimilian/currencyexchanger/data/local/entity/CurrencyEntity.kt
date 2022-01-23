package com.maksimilian.currencyexchanger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class CurrencyEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val symbol: String,
    val shortName: String
) {
    companion object {
        const val TABLE_NAME = "currency"
    }
}