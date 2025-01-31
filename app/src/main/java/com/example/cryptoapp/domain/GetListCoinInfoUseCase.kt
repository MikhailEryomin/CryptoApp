package com.example.cryptoapp.domain

import com.example.cryptoapp.data.model.CoinInfo

class GetListCoinInfoUseCase(private val repository: CoinInfoRepository) {

    operator fun invoke() = repository.getListCoinInfo()

}