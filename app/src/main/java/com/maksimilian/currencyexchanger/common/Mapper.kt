package com.maksimilian.currencyexchanger.common

import io.reactivex.Single

interface Mapper<IN, OUT> {
    fun map(data: IN): OUT
}

interface SingleMapper<IN, OUT> {
    fun map(data: IN): Single<OUT>
}

fun <IN, OUT> Mapper<IN, OUT>.mapList(data: List<IN>): List<OUT> = data.map { map(it) }
