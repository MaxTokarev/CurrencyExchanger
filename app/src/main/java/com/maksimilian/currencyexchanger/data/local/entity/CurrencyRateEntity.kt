package com.maksimilian.currencyexchanger.data.local.entity

import androidx.room.*
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyRateEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CurrencyEntity::class,
            parentColumns = [ID],
            childColumns = [CURRENCY_ID],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class CurrencyRateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(index = true)
    val currencyId: Int,
    val rate: Double
) {
    companion object {
        const val TABLE_NAME = "currencyRate"
    }
}

class CurrencyRateEntityJoin(
    @Embedded
    val rate: CurrencyRateEntity,
    @Relation(entity = CurrencyEntity::class, parentColumn = CURRENCY_ID, entityColumn = ID)
    val currency: CurrencyEntity
)