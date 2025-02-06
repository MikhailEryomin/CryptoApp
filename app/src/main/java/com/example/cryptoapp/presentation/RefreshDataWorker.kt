package com.example.cryptoapp.presentation

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.db.AppDatabase
import com.example.cryptoapp.data.db.CoinInfoDao
import com.example.cryptoapp.data.mapper.CoinInfoMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import com.example.cryptoapp.domain.CoinInfo
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinInfoMapper,
    private val apiService: ApiService
) : CoroutineWorker(context, workerParameters) {

//    private val coinInfoDao = AppDatabase.getInstance(context).coinPriceInfoDao()
//    private val mapper = CoinInfoMapper()

//    private val apiService = ApiFactory.apiService

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val nameListDto = apiService.getTopCoinsInfo(limit = 50)
                val namesString = mapper.mapNameListToString(nameListDto)
                val jsonContainer = apiService.getFullPriceList(fSyms = namesString)
                val coinInfoListDto = mapper.mapJsonContainerToListDto(jsonContainer)
                val coinInfoListDbModel = mapper.mapDtoListToDbModelList(coinInfoListDto)
                coinInfoDao.insertPriceList(coinInfoListDbModel)
            } catch (_: Exception) {
            }
            delay(10000)
        }
    }

    companion object {

        const val WORKER_NAME = "refresh_data"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }


    }

}