package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.CoinInfoRepositoryImpl
import com.example.cryptoapp.domain.GetCoinInfoUseCase
import com.example.cryptoapp.domain.GetListCoinInfoUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val getCoinInfoListUseCase: GetListCoinInfoUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase
) : ViewModel() {

    var priceList = getCoinInfoListUseCase.invoke()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}