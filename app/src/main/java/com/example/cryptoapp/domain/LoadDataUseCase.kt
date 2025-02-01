package com.example.cryptoapp.domain

class LoadDataUseCase(private val repository: CoinInfoRepository) {

    suspend operator fun invoke() = repository.loadData()

}