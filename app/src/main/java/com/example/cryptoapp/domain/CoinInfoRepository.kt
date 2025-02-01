package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData
import com.example.cryptoapp.data.network.CoinNameDto

interface CoinInfoRepository {

    fun getListCoinInfo(): LiveData<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>

    suspend fun loadData()

}