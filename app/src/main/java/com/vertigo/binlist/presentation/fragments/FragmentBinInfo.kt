package com.vertigo.binlist.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vertigo.binlist.data.remotesource.BinApiInfo
import com.vertigo.binlist.databinding.FragmentBinInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBinInfo: Fragment() {
    private var _binding: FragmentBinInfoBinding? = null
    private val binding get() = _binding!!

    private val binInfoViewModel: BinInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBinInfoBinding.inflate(inflater, container, false)

        val view = binding.root

        binInfoViewModel.binInfo.observe(viewLifecycleOwner) { binInfo ->
            setAllValue(binApiInfo = binInfo)
        }

        with(binding) {
            searchButton.setOnClickListener {
                val textValue = binNumberEditText.text.toString()
                binInfoViewModel.getBinInfo(binNumber = textValue)
            }
        }

        return view
    }

    private fun setAllValue(binApiInfo: BinApiInfo?) {
        with(binding) {
            brandValueTextView.text = binApiInfo?.brand
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}