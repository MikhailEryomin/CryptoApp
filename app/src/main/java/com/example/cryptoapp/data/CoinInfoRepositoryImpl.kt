package com.example.cryptoapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.db.AppDatabase
import com.example.cryptoapp.data.db.CoinInfoDao
import com.example.cryptoapp.data.mapper.CoinInfoMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.CoinInfoDto
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.domain.CoinInfoRepository
import com.example.cryptoapp.presentation.RefreshDataWorker
import com.example.cryptoapp.presentation.RefreshDataWorker.Companion.WORKER_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinInfoRepositoryImpl @Inject constructor (
    private val application: Application,
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinInfoMapper
) : CoinInfoRepository {

//    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
//    private val mapper = CoinInfoMapper()


    override fun getListCoinInfo(): LiveData<List<CoinInfo>> {
        return MediatorLiveData<List<CoinInfo>>().apply {
            addSource(coinInfoDao.getPriceList()) {
                value = mapper.mapDbModelListToEntityList(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return MediatorLiveData<CoinInfo>().apply {
            addSource(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
                value = mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            WORKER_NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}