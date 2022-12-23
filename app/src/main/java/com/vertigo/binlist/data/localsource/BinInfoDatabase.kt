package com.vertigo.binlist.data.localsource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalBinInfo::class], version = 1, exportSchema = true)
abstract class BinInfoDatabase: RoomDatabase() {
    abstract fun getBinInfoDao(): BinInfoDao
}