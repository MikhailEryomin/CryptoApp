package com.example.cryptoapp.domain

import javax.inject.Inject

class GetListCoinInfoUseCase @Inject constructor(
    private val repository: CoinInfoRepository
) {

    operator fun invoke() = repository.getListCoinInfo()

}