package com.example.cryptoapp

import android.app.Application
import android.graphics.Bitmap.Config
import androidx.work.Configuration
import com.example.cryptoapp.data.db.AppDatabase
import com.example.cryptoapp.data.mapper.CoinInfoMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.di.DaggerApplicationComponent
import com.example.cryptoapp.presentation.RefreshDataWorkerFactory
import javax.inject.Inject

class AppClass : Application(), Configuration.Provider {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    @Inject
    lateinit var refreshDataWorkerFactory: RefreshDataWorkerFactory

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(refreshDataWorkerFactory)
            .build()
}