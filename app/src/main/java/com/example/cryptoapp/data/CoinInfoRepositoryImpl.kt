package com.example.cryptoapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.cryptoapp.data.db.AppDatabase
import com.example.cryptoapp.data.mapper.CoinInfoMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.domain.CoinInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoinInfoRepositoryImpl(
    application: Application
) : CoinInfoRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinInfoMapper()

    private val apiService = ApiFactory.apiService


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

    override suspend fun loadData() {
        while (true) {
            try {
                val nameListDto = apiService.getTopCoinsInfo(limit = LIMIT)
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
        private const val LIMIT = 50
    }
}