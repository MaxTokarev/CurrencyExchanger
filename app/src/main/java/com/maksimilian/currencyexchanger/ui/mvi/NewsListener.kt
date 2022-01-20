package com.maksimilian.currencyexchanger.ui.mvi

import android.content.Context
import android.widget.Toast
import com.maksimilian.currencyexchanger.ui.screens.converter.mvi.ConverterNews
import io.reactivex.functions.Consumer

class NewsListener(
    private val context: Context
) : Consumer<ConverterNews> {

    override fun accept(news: ConverterNews) {
        when (news) {
        }
    }

    fun errorHappened(throwable: Throwable) {
        Toast.makeText(context, "Simulated error was triggered", Toast.LENGTH_SHORT).show()
    }
}