package com.vertigo.binlist.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.vertigo.binlist.data.localsource.LocalBinInfo
import com.vertigo.binlist.databinding.ItemBinListBinding

class BinInfoViewHolder(private val binding: ItemBinListBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LocalBinInfo) {
        with(binding) {
            binNumberTextView.text = item.number
            binScheme.text = item.scheme
        }
    }
}