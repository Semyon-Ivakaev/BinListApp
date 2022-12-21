package com.vertigo.binlist.domain.repository

import com.vertigo.binlist.data.remotesource.BinApiInfo

interface RemoteRepository {
    suspend fun getBinInfo(binNumber: String): BinApiInfo?
}