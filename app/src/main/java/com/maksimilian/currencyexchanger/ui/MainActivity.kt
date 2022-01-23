package com.maksimilian.currencyexchanger.ui

import android.os.Bundle
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maksimilian.currencyexchanger.R
import com.maksimilian.currencyexchanger.common.ui.ObservableSourceActivity
import com.maksimilian.currencyexchanger.databinding.ActivityMainBinding
import com.maksimilian.currencyexchanger.databinding.LayoutAccountPagerBinding
import com.maksimilian.currencyexchanger.di.injector
import com.maksimilian.currencyexchanger.ui.mvi.CurrencyBalancesFeature
import com.maksimilian.currencyexchanger.ui.mvi.MainViewModel
import com.maksimilian.currencyexchanger.ui.mvi.NewsListener
import com.maksimilian.currencyexchanger.ui.mvi.UiEvent
import io.reactivex.functions.Consumer
import javax.inject.Inject

class MainActivity : ObservableSourceActivity<UiEvent>(), Consumer<MainViewModel> {

    private val fromCardAccountsAdapter = CardAccountsAdapter()
    private val toCardAccountsAdapter = CardAccountsAdapter()

    @Inject
    lateinit var reducer: CurrencyBalancesFeature
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MainActivityBindings(this, reducer, NewsListener(this)).setup(this)
        setupUi()
    }

    override fun accept(vm: MainViewModel) {
        fromCardAccountsAdapter.submitList(vm.fromAccounts)
        toCardAccountsAdapter.submitList(vm.toAccounts)
        with(binding) {
            tvHintExchangeFrom.text =
                getString(R.string.ph_from_account_exchange, vm.fromCurrencyShortName)
            tvHintExchangeTo.text =
                getString(R.string.ph_to_account_exchange, vm.toCurrencyShortName)
            tvCurrentCurrencyRate.text = getString(R.string.ph_exchange_by, vm.currencyRate)
            pbLoadingAccounts.isVisible = vm.isAccountsLoading
            pbLoadingExchange.isVisible = vm.isExchangeLoading
            etFromAccount.addTextChangedListener {
                onNext(UiEvent.FromAccountTextEntered(it.toString()))
            }
            etToAccount.addTextChangedListener {
                onNext(UiEvent.ToAccountTextEntered(it.toString()))
            }
        }
    }

    private fun setupUi() {
        with(binding) {
            setupViewPager(includeFromAccount, fromCardAccountsAdapter) { position ->
                UiEvent.FromAccountScrolledTo(position)
            }
            setupViewPager(includeToAccount, toCardAccountsAdapter) { position ->
                UiEvent.ToAccountScrolledTo(position)
            }
            btnExchange.setOnClickListener {
                UiEvent.ExchangeClicked
            }
        }
    }

    private inline fun setupEt(
        et: EditText,
        crossinline onTextChanged: (String) -> Unit
    ) {
        with(et) {
            addTextChangedListener { onTextChanged(it.toString()) }
        }
    }

    private inline fun setupViewPager(
        pagerBinding: LayoutAccountPagerBinding,
        adapter: CardAccountsAdapter,
        crossinline onChangePosition: (position: Int) -> UiEvent
    ) {
        with(pagerBinding) {
            pager.adapter = adapter
            pager.setPageTransformer(
                MarginPageTransformer(
                    resources.getDimensionPixelSize(R.dimen.account_page_margin)
                )
            )
            TabLayoutMediator(tabLayout, pager) { _, _ ->
                // do nothing
            }.attach()
            val onTabListener = object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let { position -> onNext(onChangePosition(position)) }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // do nothing
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // do nothing
                }
            }
            tabLayout.addOnTabSelectedListener(onTabListener)
        }
    }
}