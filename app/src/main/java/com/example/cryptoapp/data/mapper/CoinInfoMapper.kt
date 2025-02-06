package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.db.CoinInfoDbModel
import com.example.cryptoapp.data.network.CoinInfoDto
import com.example.cryptoapp.data.network.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.CoinNameListDto
import com.example.cryptoapp.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class CoinInfoMapper @Inject constructor(){

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

    fun mapDtoListToDbModelList(dtoList: List<CoinInfoDto>): List<CoinInfoDbModel> =
        dtoList.map { mapDtoToToDbModel(it) }

    fun mapDbModelListToEntityList(dbModelList: List<CoinInfoDbModel>): List<CoinInfo> =
        dbModelList.map { mapDbModelToEntity(it) }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo =
        CoinInfo(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            price = dbModel.price,
            lowDay = dbModel.lowDay,
            highDay = dbModel.highDay,
            lastMarket = dbModel.lastMarket,
            lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
            imageUrl = dbModel.imageUrl
        )

    private fun mapDtoToToDbModel(dto: CoinInfoDto): CoinInfoDbModel =
        CoinInfoDbModel(
            fromSymbol = dto.fromSymbol,
            toSymbol = dto.toSymbol,
            price = dto.price,
            lowDay = dto.lowDay,
            highDay = dto.highDay,
            lastMarket = dto.lastMarket,
            lastUpdate = dto.lastUpdate,
            imageUrl = BASE_IMAGE_URL + dto.imageUrl
        )

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {
        private const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }

}