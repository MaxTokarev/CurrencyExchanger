package com.maksimilian.currencyexchanger.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maksimilian.currencyexchanger.data.db.entity.CurrencyAccountEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class CurrencyAccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
    companion object {
        const val TABLE_NAME = "currency_account"
    }
}