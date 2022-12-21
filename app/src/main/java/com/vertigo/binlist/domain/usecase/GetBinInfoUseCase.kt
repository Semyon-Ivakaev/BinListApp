package com.vertigo.binlist.domain.usecase

import com.vertigo.binlist.data.remotesource.BinApiInfo
import com.vertigo.binlist.domain.repository.RemoteRepository
import javax.inject.Inject

class GetBinInfoUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend fun execute(binNumber: String): BinApiInfo? {
        return remoteRepository.getBinInfo(binNumber = binNumber)
    }
}