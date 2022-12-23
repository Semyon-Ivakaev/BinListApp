package com.vertigo.binlist.domain.usecase

import com.vertigo.binlist.data.localsource.LocalBinInfo
import com.vertigo.binlist.domain.repository.LocalRepository
import javax.inject.Inject

class AddBinInDBUseCase @Inject constructor(
    private val localRepository: LocalRepository
){
    suspend fun execute(binInfo: LocalBinInfo) {
        localRepository.addBinInfoToFavorites(binInfo = binInfo)
    }
}