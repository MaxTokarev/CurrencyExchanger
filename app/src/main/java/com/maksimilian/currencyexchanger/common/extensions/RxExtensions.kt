package com.maksimilian.currencyexchanger.common.extensions

import io.reactivex.Observable
import io.reactivex.ObservableSource

fun <T1, T2> combineLatest(
    o1: ObservableSource<T1>,
    o2: ObservableSource<T2>
): ObservableSource<Pair<T1, T2>> =
    Observable.combineLatest(o1, o2) { t1, t2 -> Pair(t1, t2) }