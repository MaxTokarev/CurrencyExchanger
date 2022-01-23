package com.maksimilian.currencyexchanger.data.local.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.maksimilian.currencyexchanger.data.local.entity.CurrencyAccountEntity.Companion.TABLE_NAME

const val CURRENCY_ID = "currencyId"
const val ID = "id"

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CurrencyEntity::class,
            parentColumns = [ID],
            childColumns = [CURRENCY_ID],
            onDelete = CASCADE
        )
    ]
)
class CurrencyAccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val balance: Double,
    @ColumnInfo(index = true)
    val currencyId: Int
) {
    companion object {
        const val TABLE_NAME = "currencyAccount"
    }
}

class CurrencyAccountEntityJoin(
    @Embedded
    val account: CurrencyAccountEntity,
    @Relation(entity = CurrencyEntity::class, parentColumn = CURRENCY_ID, entityColumn = ID)
    val currency: CurrencyEntity
)