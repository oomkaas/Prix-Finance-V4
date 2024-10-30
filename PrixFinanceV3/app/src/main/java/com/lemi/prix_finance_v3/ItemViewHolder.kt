package com.lemi.prix_finance_v3

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lemi.prix_finance_v3.databinding.ItemLayoutBinding

class ItemViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item) {
        binding.itemTitle.text = item.title
        binding.itemPrice.text = item.price.toString()
        binding.itemStatus.text = item.status
    }
}