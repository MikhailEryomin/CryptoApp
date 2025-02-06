package com.example.cryptoapp.presentation

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class RefreshDataWorkerFactory @Inject constructor (
    private val workers: @JvmSuppressWildcards Map<Class<out ListenableWorker>, Provider<ChildWorkerFactory>>
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName) {
            RefreshDataWorker::class.qualifiedName -> {
                val factory = workers[RefreshDataWorker::class.java]?.get() ?:
                throw RuntimeException("Unknown worker ${RefreshDataWorker::class.qualifiedName}")
                factory.create(appContext, workerParameters,)
            }
            else -> null
        }
    }
}