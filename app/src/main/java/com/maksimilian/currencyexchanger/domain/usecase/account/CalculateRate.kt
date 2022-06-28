package com.maksimilian.currencyexchanger.domain.usecase.account

import javax.inject.Inject

interface CalculateRateUseCase : (Double, Double, Double) -> Double

class CalculateRateUseCaseImpl @Inject constructor() : CalculateRateUseCase {
    override fun invoke(count: Double, from: Double, to: Double): Double {
        return (count / from) * to
    }
}