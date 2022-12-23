package com.vertigo.binlist.domain.repository

import com.vertigo.binlist.data.localsource.LocalBinInfo

interface LocalRepository {

    suspend fun addBinInfoToFavorites(binInfo: LocalBinInfo)

    suspend fun getBinListFromDB(): List<LocalBinInfo>
}