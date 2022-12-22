package com.vertigo.binlist.presentation.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertigo.binlist.data.remotesource.BinApiResponse
import com.vertigo.binlist.domain.usecase.GetBinInfoUseCase
import com.vertigo.binlist.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinInfoViewModel @Inject constructor(
    private val getBinInfoUseCase: GetBinInfoUseCase
): ViewModel() {

    private val _binInfo: MutableLiveData<Resource<BinApiResponse>> = MutableLiveData()
    val binInfo: LiveData<Resource<BinApiResponse>> get() = _binInfo

    fun getBinInfo(binNumber: String) {
        viewModelScope.launch {
            _binInfo.postValue(Resource.Loading())

            val response = getBinInfoUseCase.execute(binNumber = binNumber)
            if (response.isSuccessful) {
                response.body().let { data ->
                    _binInfo.postValue(Resource.Success(data))
                }
            } else {
                val responseCode = response.code()
                val errorMessage = if (responseCode == 429) "Превышено количество запросов" else "Некорректный запрос"
                _binInfo.postValue(Resource.Error(message = errorMessage))
            }
        }
    }
}