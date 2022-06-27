package com.maksimilian.currencyexchanger.common

interface Mapper<I, O> {
    fun map(data: I): O
}

fun <I, O> Mapper<I, O>.mapList(data: List<I>): List<O> = data.map { map(it) }
