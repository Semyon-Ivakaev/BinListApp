package com.vertigo.binlist.domain.repository

import com.vertigo.binlist.data.remotesource.BinApiResponse
import retrofit2.Response

interface RemoteRepository {
    suspend fun getBinInfo(binNumber: String): Response<BinApiResponse>
}