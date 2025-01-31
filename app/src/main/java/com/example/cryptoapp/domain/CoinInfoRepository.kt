package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData
import com.example.cryptoapp.data.model.CoinInfo

interface CoinInfoRepository {

    fun getListCoinInfo(): LiveData<List<CoinInfo>>

    fun getCoinInfo(): LiveData<CoinInfo>

}