package com.vertigo.binlist.data.remotesource

import com.google.gson.annotations.SerializedName

data class BinApiInfo(
    @SerializedName("brand")
    val brand: String
)