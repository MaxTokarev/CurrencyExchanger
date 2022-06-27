package com.maksimilian.currencyexchanger.common.extensions

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)