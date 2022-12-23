package com.vertigo.binlist.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.vertigo.binlist.data.localsource.LocalBinInfo

class DiffBinUtil: DiffUtil.ItemCallback<LocalBinInfo>() {
    override fun areItemsTheSame(oldItem: LocalBinInfo, newItem: LocalBinInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LocalBinInfo, newItem: LocalBinInfo): Boolean {
        return oldItem.id == newItem.id
    }
}