package com.maksimilian.currencyexchanger.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maksimilian.currencyexchanger.common.extensions.layoutInflater
import com.maksimilian.currencyexchanger.databinding.ItemAccountBinding

class CardAccountsAdapter :
    ListAdapter<CurrencyAccountUi, CardAccountsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemAccountBinding.inflate(parent.layoutInflater, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CurrencyAccountUi) {
            with(binding) {
                tvAccountName.text = item.name
                tvAccountBalance.text = item.balance
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CurrencyAccountUi>() {
            override fun areItemsTheSame(
                oldItem: CurrencyAccountUi,
                newItem: CurrencyAccountUi
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CurrencyAccountUi,
                newItem: CurrencyAccountUi
            ): Boolean = oldItem == newItem
        }
    }
}