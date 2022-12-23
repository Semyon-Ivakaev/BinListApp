package com.vertigo.binlist.data.localsource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BinInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(binInfo: LocalBinInfo)

    @Query("SELECT * FROM request_bin_list")
    fun getBinList(): List<LocalBinInfo>
}