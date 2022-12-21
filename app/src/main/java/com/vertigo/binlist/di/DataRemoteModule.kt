package com.vertigo.binlist.di

import com.vertigo.binlist.data.remotesource.RemoteRepositoryImpl
import com.vertigo.binlist.domain.repository.RemoteRepository
import com.vertigo.binlist.domain.usecase.GetBinInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataRemoteModule {

    @Provides
    fun provideRemoteRepository(): RemoteRepository {
        return RemoteRepositoryImpl()
    }

    @Provides
    fun provideGetBinInfoUseCase(remoteRepository: RemoteRepository): GetBinInfoUseCase {
        return GetBinInfoUseCase(remoteRepository = remoteRepository)
    }
}