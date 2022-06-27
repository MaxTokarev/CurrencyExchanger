package com.maksimilian.currencyexchanger.ui.mvi

import android.content.Context
import android.widget.Toast
import com.maksimilian.currencyexchanger.R
import com.maksimilian.currencyexchanger.presentation.model.CurrencyBalancesNews
import io.reactivex.functions.Consumer

class NewsListener(
    private val context: Context
) : Consumer<CurrencyBalancesNews> {

    override fun accept(news: CurrencyBalancesNews) {
        when (news) {
            is CurrencyBalancesNews.Error -> errorHappened(news.throwable)
            CurrencyBalancesNews.SuccessExchange -> Toast.makeText(
                context,
                context.getString(R.string.successful_exchange),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun errorHappened(throwable: Throwable) {
        Toast.makeText(context, "Error - ${throwable.message}", Toast.LENGTH_SHORT).show()
    }
}