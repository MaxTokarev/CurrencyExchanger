package com.maksimilian.currencyexchanger.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maksimilian.currencyexchanger.R
import com.maksimilian.currencyexchanger.common.extensions.layoutInflater
import com.maksimilian.currencyexchanger.databinding.ItemAccountBinding
import com.maksimilian.currencyexchanger.ui.model.CurrencyAccountUi

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
                tvAccountName.text = root.context.getString(R.string.ph_account_name, item.name)
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