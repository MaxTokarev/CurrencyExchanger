package com.maksimilian.currencyexchanger.domain.usecase.account

interface CalculateRateUseCase {
    /**
     * from / base * to
     */
    fun calculate(from: Double, to: Double): Double

    class Base(private val count: Double): CalculateRateUseCase {
        override fun calculate(from: Double, to: Double): Double {
            return (count / from) * to
        }
    }
}