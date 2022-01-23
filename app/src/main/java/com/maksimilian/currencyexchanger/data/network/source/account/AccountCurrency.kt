package com.maksimilian.currencyexchanger.data.network.source.account

enum class AccountCurrency(val code: String) {
    EURO("EUR"), US("USD"), POUND("GBP"), RUB("RUB");

    companion object {
        private val currencyToId: Map<AccountCurrency, Int> =
            values().associateWith { it.ordinal + 1 }

        fun getIdByCode(code: String): Int =
            currencyToId.filterKeys { it.code == code }
                .values
                .lastOrNull()
                ?: error("Cannot find currency by given code - $code")

        val AccountCurrency.id: Int get() = currencyToId.getOrElse(this) {
            error("Cannot find id by given account - $this")
        }
    }
}