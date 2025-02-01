package com.example.cryptoapp.domain

class LoadDataUseCase(private val repository: CoinInfoRepository) {

    operator fun invoke() = repository.loadData()

}