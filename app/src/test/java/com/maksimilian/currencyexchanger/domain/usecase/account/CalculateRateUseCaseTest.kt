package com.maksimilian.currencyexchanger.domain.usecase.account

import junit.framework.TestCase
import org.junit.Test

class CalculateRateUseCaseTest : TestCase() {
    val calculator: CalculateRateUseCase = CalculateRateUseCase.Base()
    @Test
    fun test_calculate_1_usd_to_rub() {
        val usdRate = 1.13
        val rubRate = 88.0
        val oneUsd = 1.0

        val result = calculator.calculate(oneUsd, usdRate, rubRate)
        val expected = 77

        assertTrue(result.toInt() == expected)
    }
}