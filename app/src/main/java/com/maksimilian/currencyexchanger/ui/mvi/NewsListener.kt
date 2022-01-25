package com.maksimilian.currencyexchanger.ui.mvi

import android.content.Context
import android.widget.Toast
import io.reactivex.functions.Consumer

class NewsListener(
    private val context: Context
) : Consumer<CurrencyBalancesFeature.News> {

    override fun accept(news: CurrencyBalancesFeature.News) {
        when (news) {
            is CurrencyBalancesFeature.News.Error -> errorHappened(news.throwable)
        }
    }

    private fun errorHappened(throwable: Throwable) {
        Toast.makeText(context, "Error - ${throwable.message}", Toast.LENGTH_SHORT).show()
    }
}