package com.example.cryptoapp.domain

import com.example.cryptoapp.data.model.CoinInfo

class GetCoinInfoUseCase(private val repository: CoinInfoRepository) {

    operator fun invoke() = repository.getCoinInfo()

}