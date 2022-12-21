package com.vertigo.binlist.data.remotesource

import com.vertigo.binlist.data.remotesource.api.RetrofitInstance
import com.vertigo.binlist.domain.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteRepositoryImpl: RemoteRepository {

    private val retrofitApi = RetrofitInstance.binInfoRetrofitApiService

    override suspend fun getBinInfo(binNumber: String): BinApiInfo? {
        var response: BinApiInfo? = null
        withContext(Dispatchers.Default) {
            response = retrofitApi.getBinInfo(binNumber = binNumber)

        }
        return response
    }
}