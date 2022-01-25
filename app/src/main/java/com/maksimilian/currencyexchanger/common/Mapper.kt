package com.maksimilian.currencyexchanger.common

interface Mapper<IN, OUT> {
    fun map(data: IN): OUT
}

fun <IN, OUT> Mapper<IN, OUT>.mapList(data: List<IN>): List<OUT> = data.map { map(it) }
