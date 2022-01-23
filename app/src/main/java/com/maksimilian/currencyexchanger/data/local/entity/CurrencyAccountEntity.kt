package com.maksimilian.currencyexchanger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyAccountEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class CurrencyAccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val balance: Double,
    val name: String,
    val symbol: String,
    val shortName: String
) {
    companion object {
        const val TABLE_NAME = "currency_account"
    }
}