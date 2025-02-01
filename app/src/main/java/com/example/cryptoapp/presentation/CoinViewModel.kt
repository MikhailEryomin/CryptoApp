package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.CoinInfoRepositoryImpl
import com.example.cryptoapp.domain.GetCoinInfoUseCase
import com.example.cryptoapp.domain.GetListCoinInfoUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinInfoRepositoryImpl(application)
    private val loadDataUseCase = LoadDataUseCase(repository)
    private val getCoinInfoListUseCase = GetListCoinInfoUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    var priceList = getCoinInfoListUseCase.invoke()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}