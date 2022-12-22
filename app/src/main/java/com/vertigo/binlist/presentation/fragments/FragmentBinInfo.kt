package com.vertigo.binlist.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vertigo.binlist.data.remotesource.BinApiResponse
import com.vertigo.binlist.databinding.FragmentBinInfoBinding
import com.vertigo.binlist.utils.Resource
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

        binInfoViewModel.binInfo.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> setAllValue(binApiInfo = resource.data)
                is Resource.Error -> Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                is Resource.Loading -> clearBinInfo()
            }
        }

        with(binding) {
            searchButton.setOnClickListener {
                val textValue = binNumberEditText.text.toString()
                binInfoViewModel.getBinInfo(binNumber = textValue)
            }
        }

        return view
    }

    private fun setAllValue(binApiInfo: BinApiResponse?) {
        with(binding) {
            modelCardView.isVisible = true
            brandValueTextView.text = binApiInfo?.brand
            schemeValueTextView.text = binApiInfo?.scheme
            lengthValueTextView.text = binApiInfo?.number?.length.toString()
            luhnValueTextView.text = binApiInfo?.number?.luhn.toString()
            typeValueTextView.text = binApiInfo?.type
            prepaidValueTextView.text = binApiInfo?.prepaid.toString()
            countryValueTextView.text = "${binApiInfo?.country?.emoji} ${binApiInfo?.country?.name}"
            geoValueTextView.text = "(latitude: ${binApiInfo?.country?.latitude}, longitude: ${binApiInfo?.country?.longitude})"
            bankValueTextView.text = "${binApiInfo?.bank?.name}, ${binApiInfo?.bank?.city}"
            bankUrlTextView.text = binApiInfo?.bank?.url
            phoneTextView.text = binApiInfo?.bank?.phone
        }
    }

    private fun clearBinInfo() {
        with(binding) {
            modelCardView.isVisible = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}