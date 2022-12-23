package com.vertigo.binlist.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vertigo.binlist.data.localsource.LocalBinInfo
import com.vertigo.binlist.databinding.ItemBinListBinding

class BinAdapter: ListAdapter<LocalBinInfo, RecyclerView.ViewHolder>(DiffBinUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinListBinding.inflate(inflater, parent, false)
        return BinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BinInfoViewHolder).bind(getItem(position))
    }
}