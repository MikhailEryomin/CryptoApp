package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.CoinInfoRepositoryImpl
import com.example.cryptoapp.data.db.AppDatabase
import com.example.cryptoapp.data.db.CoinInfoDao
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import com.example.cryptoapp.domain.CoinInfoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepository(impl: CoinInfoRepositoryImpl): CoinInfoRepository


    companion object {

        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(application: Application): CoinInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

    }

}