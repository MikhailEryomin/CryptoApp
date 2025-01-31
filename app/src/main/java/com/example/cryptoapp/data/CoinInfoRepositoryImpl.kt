package com.example.cryptoapp.data

import androidx.lifecycle.LiveData
import com.example.cryptoapp.data.model.CoinInfo
import com.example.cryptoapp.domain.CoinInfoRepository

class CoinInfoRepositoryImpl: CoinInfoRepository {
    override fun getListCoinInfo(): LiveData<List<CoinInfo>> {
        TODO("Not yet implemented")
    }

    override fun getCoinInfo(): LiveData<CoinInfo> {
        TODO("Not yet implemented")
    }
}