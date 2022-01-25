package com.maksimilian.currencyexchanger.ui.mvi

import androidx.annotation.StringRes
import com.maksimilian.currencyexchanger.R

interface ExchangeValidator {
    sealed class Result {
        object Valid : Result()
        class InValid(@StringRes val causeRes: Int) : Result()
    }

    fun validate(input: String): Result

    class IsNotEmpty : ExchangeValidator {
        override fun validate(input: String): Result =
            when {
                input.isEmpty() -> Result.InValid(R.string.err_input_is_empty)
                else -> Result.Valid
            }
    }

    class CanConvertToDouble : ExchangeValidator {
        override fun validate(input: String): Result = when {
            input.toDoubleOrNull() == null -> Result.InValid(R.string.err_input_invalid)
            else -> Result.Valid
        }
    }

    companion object : ExchangeValidator {
        override fun validate(input: String): Result {
            return listOf(IsNotEmpty(), CanConvertToDouble())
                .map { it.validate(input) }
                .firstOrNull { it is Result.InValid } ?: Result.Valid
        }
    }
}