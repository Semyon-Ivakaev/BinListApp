package com.vertigo.binlist.data.remotesource.api

import com.vertigo.binlist.data.remotesource.BinApiInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApiService {

    @GET("/{bin_number}")
    suspend fun getBinInfo(
        @Path("bin_number")
        binNumber: String): BinApiInfo
}