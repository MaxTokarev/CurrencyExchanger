package com.maksimilian.currencyexchanger.ui.screens.converter.mvi

sealed class ConverterNews {
    object UnableToConvert : ConverterNews()
}