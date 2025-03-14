package com.example.cryptoapp.presentation

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {

    fun create(
        apiContext: Context,
        workerParameters: WorkerParameters
    ): ListenableWorker
}