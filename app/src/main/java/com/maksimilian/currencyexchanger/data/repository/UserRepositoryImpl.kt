package com.maksimilian.currencyexchanger.data.repository

import com.maksimilian.currencyexchanger.common.mapList
import com.maksimilian.currencyexchanger.data.local.source.user.CurrencyAccountsLocalDataSource
import com.maksimilian.currencyexchanger.data.mappers.CurrencyAccountMappersDataToDomain
import com.maksimilian.currencyexchanger.data.models.CurrencyData
import com.maksimilian.currencyexchanger.data.network.source.account.CurrencyAccountNetworkDataSource
import com.maksimilian.currencyexchanger.domain.model.CurrencyAccount
import com.maksimilian.currencyexchanger.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: CurrencyAccountsLocalDataSource,
    private val networkDataSource: CurrencyAccountNetworkDataSource,
    private val mappersDataToDomain: CurrencyAccountMappersDataToDomain
) : UserRepository {
    override fun fetchIfRequireAccounts(currencies: List<CurrencyData>): Completable =
        getAllAccounts()
            .flatMapCompletable { accounts ->
                if (accounts.isEmpty()) fetchUsersAndInsert(currencies) else Completable.complete()
            }

    override fun observeAccounts(): Observable<List<CurrencyAccount>> =
        localDataSource.observeAccounts().map { mappersDataToDomain.mapList(it) }

    override fun getAllAccounts(): Single<List<CurrencyAccount>> =
        localDataSource.getAllAccounts().map { mappersDataToDomain.mapList(it) }

    private fun fetchUsersAndInsert(currencies: List<CurrencyData>): Completable =
        networkDataSource.fetchUserAccountsFor(currencies)
            .flatMapCompletable { localDataSource.upInsertAccounts(it) }
}