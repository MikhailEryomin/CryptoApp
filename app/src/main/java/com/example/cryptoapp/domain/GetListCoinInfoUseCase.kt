package com.example.cryptoapp.domain

class GetListCoinInfoUseCase(private val repository: CoinInfoRepository) {

    operator fun invoke() = repository.getListCoinInfo()

}