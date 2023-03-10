package com.vertigo.binlist.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vertigo.binlist.data.localsource.LocalBinInfo
import com.vertigo.binlist.data.remotesource.BinApiResponse
import com.vertigo.binlist.databinding.FragmentBinInfoBinding
import com.vertigo.binlist.presentation.adapters.BinAdapter
import com.vertigo.binlist.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBinInfo: Fragment() {
    private var _binding: FragmentBinInfoBinding? = null
    private val binding get() = _binding!!

    private val binInfoViewModel: BinInfoViewModel by viewModels()
    private lateinit var binAdapter: BinAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBinInfoBinding.inflate(inflater, container, false)

        val view = binding.root
        createAdapter()

        binInfoViewModel.binInfo.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> setAllValue(binApiInfo = resource.data)
                is Resource.Error -> Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                is Resource.Loading -> clearBinInfo()
            }
        }

        binInfoViewModel.binList.observe(viewLifecycleOwner) {
            resource -> updateAdapter(binList = resource)
        }

        with(binding) {
            searchButton.setOnClickListener {
                val textValue = binNumberEditText.text.toString().replace(" ", "")
                binInfoViewModel.getBinInfo(binNumber = textValue)
            }
        }

        return view
    }

    private fun setAllValue(binApiInfo: BinApiResponse?) {
        with(binding) {
            modelCardView.isVisible = true
            brandValueTextView.text = getCorrectStringData(binApiInfo?.brand)
            schemeValueTextView.text = getCorrectStringData(binApiInfo?.scheme)
            lengthValueTextView.text = getCorrectStringData(binApiInfo?.number?.length.toString())
            luhnValueTextView.text = getCorrectBooleanData(binApiInfo?.number?.luhn)
            typeValueTextView.text = getCorrectStringData(binApiInfo?.type)
            prepaidValueTextView.text = getCorrectBooleanData(binApiInfo?.prepaid)
            countryValueTextView.text = "${getCorrectStringData(binApiInfo?.country?.emoji)} ${getCorrectStringData(binApiInfo?.country?.name)}"
            geoValueTextView.text = "(latitude: ${getCorrectIntData(binApiInfo?.country?.latitude)}, longitude: ${getCorrectIntData(binApiInfo?.country?.longitude)})"
            bankValueTextView.text = "${getCorrectStringData(binApiInfo?.bank?.name)} ${getCorrectStringData(binApiInfo?.bank?.city)}"

            bankUrlTextView.apply {
                text = getCorrectStringData(binApiInfo?.bank?.url)
                setOnClickListener {
                    initWebIntent(binApiInfo?.bank?.url)
                }
            }
            phoneTextView.apply {
                text = getCorrectStringData(binApiInfo?.bank?.phone)
                setOnClickListener {
                    initPhoneIntent(binApiInfo?.bank?.phone)
                }
            }

                countryContainer.setOnClickListener {
                    initMapsIntent(binApiInfo?.country?.latitude, binApiInfo?.country?.longitude)
                }
            }
        }

    private fun getCorrectStringData(text: String?): String {
        return if (text.isNullOrEmpty()) "" else text
    }

    private fun getCorrectIntData(number: Int?): String {
        return if (number.toString().isEmpty()) "-" else number.toString()
    }

    private fun getCorrectBooleanData(value: Boolean?): String {
        return if (value == true) "Yes" else "No"
    }

    private fun clearBinInfo() {
        with(binding) {
            modelCardView.isVisible = false
        }
    }

    private fun createAdapter() {
        binAdapter = BinAdapter()
        layoutManager = GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
        with(binding) {
            historyRecycler.adapter = binAdapter
            historyRecycler.layoutManager = layoutManager
            historyRecycler.itemAnimator = DefaultItemAnimator()
        }
    }

    private fun updateAdapter(binList: List<LocalBinInfo>) {
        binAdapter = BinAdapter()
        binAdapter.submitList(binList)
        with(binding) {
            historyRecycler.adapter = binAdapter
            historyRecycler.layoutManager = layoutManager
            historyRecycler.itemAnimator = DefaultItemAnimator()
        }
    }

    private fun initMapsIntent(latitude: Int?, longitude: Int?) {
        val gmmIntentUri = Uri.parse("geo:${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun initPhoneIntent(phoneNumber: String?) {
        val phoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber))
        startActivity(phoneIntent)
    }


    private fun initWebIntent(webUrl: String?) {
        val url = if (webUrl?.contains("https://") == true) webUrl else "https://${webUrl}"
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(webIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}