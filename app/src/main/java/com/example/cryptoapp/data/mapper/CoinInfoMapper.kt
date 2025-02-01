package com.example.cryptoapp.data.mapper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.cryptoapp.data.db.CoinInfoDbModel
import com.example.cryptoapp.data.network.CoinInfoDto
import com.example.cryptoapp.data.network.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.CoinNameListDto
import com.example.cryptoapp.domain.CoinInfo
import com.google.gson.Gson

class CoinInfoMapper {

    fun mapDtoToToDbModel(dto: CoinInfoDto): CoinInfoDbModel =
        CoinInfoDbModel(
            fromSymbol = dto.fromSymbol,
            toSymbol = dto.toSymbol,
            price = dto.price,
            lowDay = dto.lowDay,
            highDay = dto.highDay,
            lastMarket = dto.lastMarket,
            lastUpdate = dto.lastUpdate,
            imageUrl = dto.imageUrl
        )

    fun mapJsonContainerToListDto(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = ArrayList<CoinInfoDto>()
        val jsonObject = jsonContainer.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNameListToString(names: CoinNameListDto): String =
        names.data?.map { it.coinName?.name }?.joinToString(",") ?: ""

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo =
        CoinInfo(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            price = dbModel.price,
            lowDay = dbModel.lowDay,
            highDay = dbModel.highDay,
            lastMarket = dbModel.lastMarket,
            lastUpdate = dbModel.lastUpdate,
            imageUrl = dbModel.imageUrl
        )

    fun mapDtoListToDbModelList(dtoList: List<CoinInfoDto>): List<CoinInfoDbModel> =
        dtoList.map { mapDtoToToDbModel(it) }

    fun mapDbModelListToEntityList(dbModelList: List<CoinInfoDbModel>): List<CoinInfo> =
        dbModelList.map { mapDbModelToEntity(it) }


}