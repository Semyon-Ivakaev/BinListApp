package com.vertigo.binlist.di

import com.vertigo.binlist.data.remotesource.RemoteRepositoryImpl
import com.vertigo.binlist.data.remotesource.BinApiService
import com.vertigo.binlist.domain.repository.RemoteRepository
import com.vertigo.binlist.domain.usecase.GetBinInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataRemoteModule {
    @Provides
    fun providesLogger() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    fun provideHttpClient() = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(providesLogger())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): BinApiService = Retrofit.Builder()
        .baseUrl("https://lookup.binlist.net")
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideHttpClient())
        .build()
        .create(BinApiService::class.java)

    @Provides
    fun provideRemoteRepository(): RemoteRepository {
        return RemoteRepositoryImpl(binApiService =  provideRetrofit())
    }

    @Provides
    fun provideGetBinInfoUseCase(remoteRepository: RemoteRepository): GetBinInfoUseCase {
        return GetBinInfoUseCase(remoteRepository = remoteRepository)
    }
}