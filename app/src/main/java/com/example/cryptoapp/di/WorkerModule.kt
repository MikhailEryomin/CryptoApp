package com.example.cryptoapp.di

import androidx.work.ListenableWorker
import com.example.cryptoapp.presentation.ChildWorkerFactory
import com.example.cryptoapp.presentation.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @WorkerKey(RefreshDataWorker::class)
    @IntoMap
    fun bindRefreshWorker(impl: RefreshDataWorker.Factory): ChildWorkerFactory

}