package com.maksimilian.currencyexchanger.common.extensions

import android.view.LayoutInflater
import android.view.View

val View.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)