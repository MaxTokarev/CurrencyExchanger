package com.maksimilian.currencyexchanger.ui

import android.os.Bundle
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import com.maksimilian.currencyexchanger.R
import com.maksimilian.currencyexchanger.common.ui.ObservableSourceActivity
import com.maksimilian.currencyexchanger.databinding.ActivityMainBinding
import com.maksimilian.currencyexchanger.databinding.LayoutAccountPagerBinding
import com.maksimilian.currencyexchanger.di.injector
import com.maksimilian.currencyexchanger.ui.mvi.*
import com.maksimilian.currencyexchanger.ui.screens.converter.mvi.ConverterWish
import io.reactivex.functions.Consumer
import javax.inject.Inject

class MainActivity : ObservableSourceActivity<ConverterWish>(), Consumer<ConverterState> {

    private val fromCardAccountsAdapter = CardAccountsAdapter()
    private val toCardAccountsAdapter = CardAccountsAdapter()

    @Inject
    lateinit var reducer: CurrencyReducerFeature
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()
        MainActivityBindings(this, reducer, NewsListener(this)).setup(this)
    }

    override fun accept(vm: ConverterState) {
        fromCardAccountsAdapter.submitList(vm.fromAccounts)
        toCardAccountsAdapter.submitList(vm.toAccounts)
        with(binding) {
            tvHintExchangeFrom.text =
                getString(R.string.ph_from_account_exchange, vm.fromCurrencyName)
            tvHintExchangeTo.text = getString(R.string.ph_to_account_exchange, vm.toCurrencyName)
            tvCurrentCurrencyRate.text = getString(R.string.ph_exchange_by, vm.currencyRate)
        }
    }

    private fun setupUi() {
        with(binding) {
            setupViewPager(includeFromAccount, fromCardAccountsAdapter)
            setupViewPager(includeToAccount, toCardAccountsAdapter)
        }
    }

    private fun setupViewPager(
        pagerBinding: LayoutAccountPagerBinding,
        adapter: CardAccountsAdapter
    ) {
        with(pagerBinding) {
            pager.adapter = adapter
            pager.setPageTransformer(
                MarginPageTransformer(
                    resources.getDimensionPixelSize(R.dimen.account_page_margin)
                )
            )
            TabLayoutMediator(tabLayout, pager) { _, _ ->
                // Do nothing
            }.attach()
        }
    }
}