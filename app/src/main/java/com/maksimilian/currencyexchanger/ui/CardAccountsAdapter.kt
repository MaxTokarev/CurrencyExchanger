package com.maksimilian.currencyexchanger.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maksimilian.currencyexchanger.common.extensions.layoutInflater
import com.maksimilian.currencyexchanger.databinding.ItemAccountBinding

class CardAccountsAdapter :
    ListAdapter<CardAccountUi, CardAccountsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemAccountBinding.inflate(parent.layoutInflater, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CardAccountUi) {
            with(binding){
                tvAccountName.text = item.name
                tvAccountBalance.text = item.balance
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CardAccountUi>() {
            override fun areItemsTheSame(oldItem: CardAccountUi, newItem: CardAccountUi): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CardAccountUi,
                newItem: CardAccountUi
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}