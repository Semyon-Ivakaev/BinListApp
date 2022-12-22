package com.vertigo.binlist.data.remotesource

import com.vertigo.binlist.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val binApiService: BinApiService): RemoteRepository {

    override suspend fun getBinInfo(binNumber: String) = binApiService.getBinInfo(binNumber = binNumber)
}