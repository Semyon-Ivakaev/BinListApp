package com.vertigo.binlist.di

import android.content.Context
import androidx.room.Room
import com.vertigo.binlist.data.localsource.BinInfoDao
import com.vertigo.binlist.data.localsource.BinInfoDatabase
import com.vertigo.binlist.data.localsource.LocalRepositoryImpl
import com.vertigo.binlist.domain.repository.LocalRepository
import com.vertigo.binlist.domain.usecase.AddBinInDBUseCase
import com.vertigo.binlist.domain.usecase.GetBinListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataLocalModule {
    @Provides
    @Singleton
    fun provideBinInfoDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, BinInfoDatabase::class.java, "bin_info_database"
    ).build()

    @Provides
    fun provideBinInfoDao(appDatabase: BinInfoDatabase): BinInfoDao {
        return appDatabase.getBinInfoDao()
    }

    @Provides
    fun provideLocalRepository(binInfoDao: BinInfoDao): LocalRepository {
        return LocalRepositoryImpl(binInfoDao = binInfoDao)
    }

    @Provides
    fun provideAddBinInDBUseCase(localRepository: LocalRepository): AddBinInDBUseCase {
        return AddBinInDBUseCase(localRepository = localRepository)
    }

    @Provides
    fun provideGetBinListUseCase(localRepository: LocalRepository): GetBinListUseCase {
        return GetBinListUseCase(localRepository = localRepository)
    }
}