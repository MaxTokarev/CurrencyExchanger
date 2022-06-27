package com.maksimilian.currencyexchanger.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRatesEntity.Companion.TABLE_NAME

const val BASE_CURRENCY_ID = "baseCurrencyId"

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CurrencyEntity::class,
            parentColumns = [ID],
            childColumns = [BASE_CURRENCY_ID],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class CurrencyRatesEntity(
    val lastSyncTimestamp: Long,
    @PrimaryKey
    val baseCurrencyId: Int
) {

    companion object {
        const val TABLE_NAME = "currencyRates"
    }
}

class CurrencyRatesEntityJoin(
    @Embedded
    val rates: CurrencyRatesEntity,
    @Relation(entity = CurrencyEntity::class, parentColumn = BASE_CURRENCY_ID, entityColumn = ID)
    val baseCurrency: CurrencyEntity
)