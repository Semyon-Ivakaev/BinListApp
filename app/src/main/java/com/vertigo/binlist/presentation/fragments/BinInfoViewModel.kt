package com.vertigo.binlist.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertigo.binlist.data.localsource.LocalBinInfo
import com.vertigo.binlist.data.remotesource.BinApiResponse
import com.vertigo.binlist.domain.usecase.AddBinInDBUseCase
import com.vertigo.binlist.domain.usecase.GetBinInfoUseCase
import com.vertigo.binlist.domain.usecase.GetBinListUseCase
import com.vertigo.binlist.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinInfoViewModel @Inject constructor(
    private val getBinInfoUseCase: GetBinInfoUseCase,
    private val getBinListUseCase: GetBinListUseCase,
    private val addBinInDBUseCase: AddBinInDBUseCase
): ViewModel() {

    private val _binInfo: MutableLiveData<Resource<BinApiResponse>> = MutableLiveData()
    val binInfo: LiveData<Resource<BinApiResponse>> get() = _binInfo
    private val _binList: MutableLiveData<List<LocalBinInfo>> = MutableLiveData()
    val binList: LiveData<List<LocalBinInfo>> get() = _binList

    init {
        getBinListFromDb()
    }

    /**
     * binNumber - номер BIN без пробелов
     * Если передали пустоту - то выводим Toast с сообщение.
     * Иначе делаем запрос по номеру.
     *
     * Если запрос успешен, то добавляем номер в Историю запросов.
     * Иначе выводим сообщение с ошибкой. Если response.code() == 429 - "Превышено количество запросов", иначе "Некорректный запрос"
     *
     * В завершении запрашиваем из базы данных весь список запросов и обновляем историю.
     */
    fun getBinInfo(binNumber: String) {
        viewModelScope.launch {
            if (binNumber.isNotEmpty()) {
                _binInfo.postValue(Resource.Loading())
                val response = getBinInfoUseCase.execute(binNumber = binNumber)
                if (response.isSuccessful) {
                    response.body().let { data ->
                        _binInfo.postValue(Resource.Success(data))
                        val localBin = LocalBinInfo(number = binNumber, scheme = data?.scheme ?: "Scheme not found")
                        addBinInDBUseCase.execute(binInfo = localBin)
                    }
                } else {
                    val responseCode = response.code()
                    val errorMessage = if (responseCode == 429) "Превышено количество запросов" else "Некорректный запрос"
                    _binInfo.postValue(Resource.Error(message = errorMessage))
                }
                getBinListFromDb()
            } else {
                _binInfo.postValue(Resource.Error(message = "Некорректный запрос"))
            }
        }
    }

    private fun getBinListFromDb() {
        viewModelScope.launch {
            _binList.postValue(getBinListUseCase.execute())
        }
    }
}