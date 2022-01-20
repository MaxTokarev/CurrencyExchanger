package com.maksimilian.currencyexchanger.ui.mvi

sealed class ConverterEffect {
    object Empty : ConverterEffect()
}