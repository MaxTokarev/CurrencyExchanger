package com.maksimilian.currencyexchanger.common.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    companion object {
        fun <T> BaseDao<T>.upInsert(entity: T) {
            if (insert(entity) == -1L) {
                update(entity)
            }
        }

        fun <T> BaseDao<T>.upInsert(entities: List<T>) {
            val insertResults = insert(entities)
            val itemsToUpdate = insertResults
                .mapIndexedNotNull { index, insertResult ->
                    if (insertResult == -1L) entities[index] else null
                }
            update(itemsToUpdate)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entities: List<T>): Array<Long>

    @Update
    fun update(entity: T): Int

    @Update
    fun update(entity: List<T>): Int

    @Delete
    fun delete(entity: T)

    @Delete
    fun delete(entities: List<T>)
}