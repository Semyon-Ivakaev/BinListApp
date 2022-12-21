package com.vertigo.binlist.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertigo.binlist.data.remotesource.BinApiInfo
import com.vertigo.binlist.domain.usecase.GetBinInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinInfoViewModel @Inject constructor(
    private val getBinInfoUseCase: GetBinInfoUseCase
): ViewModel() {

    private val _binInfo: MutableLiveData<BinApiInfo?> = MutableLiveData()
    val binInfo: LiveData<BinApiInfo?> get() = _binInfo

    fun getBinInfo(binNumber: String) {
        viewModelScope.launch {
            val loadResult = getBinInfoUseCase.execute(binNumber = binNumber)
            _binInfo.postValue(loadResult)
        }
    }
}