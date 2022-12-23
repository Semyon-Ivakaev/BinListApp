package com.vertigo.binlist.data.localsource

import com.vertigo.binlist.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val binInfoDao: BinInfoDao
): LocalRepository {
    override suspend fun addBinInfoToFavorites(binInfo: LocalBinInfo) {
        binInfoDao.insert(binInfo = binInfo)
    }

    override suspend fun getBinListFromDB(): List<LocalBinInfo> {
        return binInfoDao.getBinList()
    }
}