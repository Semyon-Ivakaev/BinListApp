package com.vertigo.binlist.data.localsource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "request_bin_list")
data class LocalBinInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: String,
    val scheme: String
)