package com.maksimilian.currencyexchanger.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import com.maksimilian.currencyexchanger.R
import com.maksimilian.currencyexchanger.databinding.ActivityMainBinding
import com.maksimilian.currencyexchanger.databinding.LayoutAccountPagerBinding

class MainActivity : AppCompatActivity() {

    private val fromCardAccountsAdapter = CardAccountsAdapter()
    private val toCardAccountsAdapter = CardAccountsAdapter()

    private lateinit var binding: ActivityMainBinding

    private val testList
        get() = (0..3)
            .map { CardAccountUi(it, "name $it", "balance $it") }
            .shuffled()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()
        fromCardAccountsAdapter.submitList(testList)
        toCardAccountsAdapter.submitList(testList)
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
            TabLayoutMediator(tabLayout, pager) { tab, position ->
                println("tab - $tab pos - $position")
            }.attach()
        }
    }
}