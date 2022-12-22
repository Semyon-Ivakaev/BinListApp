package com.vertigo.binlist.domain.usecase

import com.vertigo.binlist.data.remotesource.BinApiResponse
import com.vertigo.binlist.domain.repository.RemoteRepository
import retrofit2.Response
import javax.inject.Inject

class GetBinInfoUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend fun execute(binNumber: String): Response<BinApiResponse> {
        return remoteRepository.getBinInfo(binNumber = binNumber)
    }
}