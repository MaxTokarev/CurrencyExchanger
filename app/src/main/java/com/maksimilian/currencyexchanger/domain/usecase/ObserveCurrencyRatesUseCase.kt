package com.maksimilian.currencyexchanger.domain.usecase

import com.maksimilian.currencyexchanger.data.mappers.CurrencyRatesMapperDataToDomain
import com.maksimilian.currencyexchanger.domain.model.CurrencyRate
import com.maksimilian.currencyexchanger.domain.model.CurrencyRates
import com.maksimilian.currencyexchanger.domain.repository.CurrencyRepository
import io.reactivex.Observable
import javax.inject.Inject

interface ObserveCurrencyRatesUseCase : () -> Observable<CurrencyRates>

class ObserveCurrencyRatesUseCaseImpl @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val mapperDataToDomain: CurrencyRatesMapperDataToDomain
) : ObserveCurrencyRatesUseCase {
    override fun invoke(): Observable<CurrencyRates> =
        currencyRepository.fetchCurrencyRates()
            .flatMapObservable { currencyRepository.observeCurrencyRates() }
            .map { mapperDataToDomain.map(it) }
}