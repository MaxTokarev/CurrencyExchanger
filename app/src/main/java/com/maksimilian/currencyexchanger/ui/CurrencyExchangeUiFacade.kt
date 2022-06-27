package com.maksimilian.currencyexchanger.ui

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.viewpager2.widget.MarginPageTransformer
import com.badoo.mvicore.byValue
import com.badoo.mvicore.modelWatcher
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maksimilian.currencyexchanger.R
import com.maksimilian.currencyexchanger.common.extensions.unsafeLazy
import com.maksimilian.currencyexchanger.databinding.FragmentCurrencyExchangeBinding
import com.maksimilian.currencyexchanger.databinding.LayoutAccountPagerBinding
import com.maksimilian.currencyexchanger.ui.model.CurrencyExchangeViewModel
import com.maksimilian.currencyexchanger.ui.adapter.CardAccountsAdapter
import com.maksimilian.currencyexchanger.ui.mvi.UiEvent

class CurrencyExchangeUiFacade(
    private val binding: FragmentCurrencyExchangeBinding,
    private val onNext: (UiEvent) -> Unit
) {
    private val fromCardAccountsAdapter by unsafeLazy { CardAccountsAdapter() }
    private val toCardAccountsAdapter by unsafeLazy { CardAccountsAdapter() }
    private val watcher = modelWatcher<CurrencyExchangeViewModel> {
        watch(CurrencyExchangeViewModel::toAccountCount, byValue()) {
            binding.etToAccount.setText(it)
        }
    }

    init {
       setupUI()
    }

    fun accept(vm: CurrencyExchangeViewModel) {
        watcher.invoke(vm)
        fromCardAccountsAdapter.submitList(vm.accounts)
        toCardAccountsAdapter.submitList(vm.accounts)
        with(binding) {
            tvHintExchangeFrom.text = binding.root.context.getString(
                R.string.ph_from_account_exchange,
                vm.currentFromAccount?.shortName
            )
            tvHintExchangeTo.text = binding.root.context.getString(
                R.string.ph_to_account_exchange,
                vm.currentToAccount?.shortName
            )
            tvCurrentCurrencyRate.text = binding.root.context.getString(
                R.string.ph_exchange_by,
                vm.currencyRate
            )
            pbLoadingAccounts.isVisible = vm.isAccountsLoading
            pbLoadingExchange.isVisible = vm.isExchangeLoading
        }
    }

    private fun setupUI(){
        with(binding) {
            setupViewPager(includeFromAccount, fromCardAccountsAdapter) { position ->
                UiEvent.FromAccountScrolledTo(position)
            }
            setupViewPager(includeToAccount, toCardAccountsAdapter) { position ->
                UiEvent.ToAccountScrolledTo(position)
            }
            etFromAccount.addTextChangedListener {
                onNext(UiEvent.FromAccountTextEntered(it.toString()))
            }
            etToAccount.addTextChangedListener {
                onNext(UiEvent.ToAccountTextEntered(it.toString()))
            }
            btnExchange.setOnClickListener {
                onNext(UiEvent.ExchangeClicked)
            }
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
                    binding.root.resources.getDimensionPixelSize(R.dimen.account_page_margin)
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