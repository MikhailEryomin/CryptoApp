package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.data.CoinInfoRepositoryImpl
import com.example.cryptoapp.domain.GetListCoinInfoUseCase
import com.example.cryptoapp.domain.LoadDataUseCase

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinInfoRepositoryImpl(application)
    private val loadDataUseCase = LoadDataUseCase(repository)
    private val getCoinInfoListUseCase = GetListCoinInfoUseCase(repository)
    var priceList = getCoinInfoListUseCase.invoke()

    init {
        loadDataUseCase {
            //after loading
            //костыль ебаный но зато какой :)
            priceList = getCoinInfoListUseCase.invoke()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}